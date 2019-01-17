//CORRIGE EL SCROLL EN LOS GRIDS CON GROUP
Ext.define(null, {
	override: 'Ext.grid.plugin.BufferedRenderer',
    onRangeFetched: function(range, start, end, options, fromLockingPartner) {
        var me = this,
            view = me.view,
            oldStart,
            rows = view.all,
            removeCount,
            increment = 0,
            calculatedTop, newTop,
            lockingPartner = (view.lockingPartner && !fromLockingPartner && !me.doNotMirror) && view.lockingPartner.bufferedRenderer,
            newRows, partnerNewRows, topAdditionSize, topBufferZone, i,
            variableRowHeight = me.variableRowHeight;

        if (view.isDestroyed) {
            return;
        }

        if (range) {

            me.scrollTop = me.view.getScrollY();
        } else {
            range = me.store.getRange(start, end);

            if (!range) {
                return;
            }
        }

        calculatedTop = start * me.rowHeight;

        if (start < rows.startIndex && end > rows.endIndex) {

            topAdditionSize = rows.startIndex - start;

            view.clearViewEl(true);
            newRows = view.doAdd(range, start);
            view.fireEvent('itemadd', range, start, newRows);
            for (i = 0; i < topAdditionSize; i++) {
                increment -= newRows[i].offsetHeight;
            }

            newTop = me.bodyTop + increment;
        } else {



            if (me.teleported || start > rows.endIndex || end < rows.startIndex) {
                newTop = calculatedTop;



                if (variableRowHeight) {
                    topBufferZone = me.scrollTop < me.position ? me.leadingBufferZone : me.trailingBufferZone;
                    if (start > topBufferZone) {
                        newTop = me.scrollTop - me.rowHeight * topBufferZone;
                    }
                }

                view.clearViewEl(true);
                me.teleported = false;
            }
            if (!rows.getCount()) {
                newRows = view.doAdd(range, start);
                view.fireEvent('itemadd', range, start, newRows);
            }

            else if (end > rows.endIndex) {
                removeCount = Math.max(start - rows.startIndex, 0);

                if (variableRowHeight) {
                    increment = rows.item(rows.startIndex + removeCount, true).offsetTop;
                }
                newRows = rows.scroll(Ext.Array.slice(range, rows.endIndex + 1 - start), 1, removeCount, start, end);
                //Arregla el problema del scroll de grid agrupado
                view.el.dom.scrollTop = me.scrollTop;
                //------------------------------------------------
                if (variableRowHeight) {

                    newTop = me.bodyTop + increment;
                } else {
                    newTop = calculatedTop;
                }
            } else
            {
                removeCount = Math.max(rows.endIndex - end, 0);
                oldStart = rows.startIndex;
                newRows = rows.scroll(Ext.Array.slice(range, 0, rows.startIndex - start), -1, removeCount, start, end);
                //Arregla el problema del scroll de grid agrupado
                view.el.dom.scrollTop = me.scrollTop;
                //------------------------------------------------
                if (variableRowHeight) {

                    newTop = me.bodyTop - rows.item(oldStart, true).offsetTop;

                    if (!rows.startIndex) {


                        if (newTop) {
                            view.setScrollY(me.position = (me.scrollTop -= newTop));
                            newTop = 0;
                        }
                    }

                    else if (newTop < 0) {
                        increment = rows.startIndex * me.rowHeight;
                        view.setScrollY(me.position = (me.scrollTop += increment));
                        newTop = me.bodyTop + increment;
                    }
                } else {
                    newTop = calculatedTop;
                }
            }


            me.position = me.scrollTop;
        }

        newTop = Math.max(Math.floor(newTop), 0);
        if (view.positionBody) {
            me.setBodyTop(newTop);
        }


        if (newRows && lockingPartner && !lockingPartner.disabled) {

            lockingPartner.scrollTop = lockingPartner.position = me.scrollTop;
            partnerNewRows = lockingPartner.onRangeFetched(null, start, end, options, true);
            if (lockingPartner.bodyTop !== newTop) {
                lockingPartner.setBodyTop(newTop);
            }

            lockingPartner.view.setScrollY(me.scrollTop);

            if (variableRowHeight && view.ownerGrid.syncRowHeights) {
                me.syncRowHeights(newRows, partnerNewRows);
            }
        }
        return newRows;
    }
});
