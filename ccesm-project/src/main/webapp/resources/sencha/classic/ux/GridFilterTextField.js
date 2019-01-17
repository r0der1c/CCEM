Ext.define("ux.GridFilterTextField", {
	extend : 'Ext.form.field.Text',
	alias: 'widget.gridfiltertextfield',
	fieldStyle: 'color: #157fcc; font-size: 18px;',
	cls: "gridfiltertextfield",
    triggers: {
    	clear: {
             weight: 0,
             cls: Ext.baseCSSPrefix + 'form-clear-trigger',
             handler: function(){
            	 this.setValue("");
            	 this.onFilter();
             }
        },
        search: {
            weight: 1,
            cls: Ext.baseCSSPrefix + 'form-search-trigger'
        }
    },
	emptyText: "Buscar...",
    enableKeyEvents: true,
    selectOnFocus: true,
    listeners: {
    	buffer: 500,
    	keyup: function(field) {
    		this.onFilter();
    	}
    },
    onFilter: function(){
    	var field = this;
    	var type = field.up("treepanel")?"treepanel":"grid";
		var grid = field.up(type);
		var filters = grid.store.getFilters();
		var searchtest;
//		var listModel = (Ext.Object.getSize(grid.store.model.fieldsMap) && grid.store.model.fieldsMap.id)?[]:
//				grid.store.model.fields.filter(function(o){return o.__proto__.name == null;}).map(function(o){return o.name;});
		var listModel = grid.getVisibleColumns().map(function(o){return o.dataIndex});
		var filterFn = function(node) {
			var listMatch = [],
			escapere = Ext.String.escapeRegex;
			searchtest = new RegExp(escapere(field.value), 'i');
			if(listModel.length)
				Ext.each(listModel, function(){
					listMatch.push(searchtest.test(node.data[this.toString()]));
				});
			else Ext.iterate(node.data, function(k, v){
				listMatch.push(searchtest.test(v));
			});
			if(type == "grid") return Ext.Array.contains(listMatch, true);
			else if(type == "treepanel"){
				var children = node.childNodes,len = children && children.length,visible = Ext.Array.contains(listMatch, true),i;
				if (!visible) {
					for (i = 0; i < len; i++) {
						if (children[i].isLeaf())visible = children[i].get('visible');
						else visible = filterFn(children[i]);
						if (visible)break;
					}
				} else for (i = 0; i < len; i++)children[i].set('visible', true);
				return visible;
			}
        };
        if (field.value) {
    		field.nameFilter = filters.add(new Ext.util.Filter({
    			filterFn: filterFn
    		}));
        } else if (field.nameFilter) {
            filters.remove(field.nameFilter);
            field.nameFilter = null;
        }
        grid.getSelectionModel().deselectAll();
        grid.getSelectionModel().clearSelections();
	}
});


