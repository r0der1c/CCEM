Ext.define("ux.SearchTextField", {
	extend : 'Ext.form.Panel',
	alias: 'widget.searchtextfield',
	border: false,
	margin: "0 0 5px 0",
	config: {
		fieldLabel: null,
		url: null,
		name: null,
		params: null,
		searchName: null,
		widthCodigo: null,
		value: null,
		text: null,
		readOnly: null,
		disabled: null,
		allowBlank: null
    },


	initComponent : function() {
		var comp = this;
        var txtCodigo = Ext.create("admin.generics.inputs.SearchCodigoTextField",{
    	   name: comp.getName(),
    	   width: comp.getWidthCodigo() || 100
        });
        var txtSearch = Ext.create("admin.generics.inputs.SearchWinTextField",{
    	   flex: 1,
    	   url: comp.getUrl(),
    	   entidad: comp.getFieldLabel(),
    	   params: comp.getParams(),
    	   name: comp.getSearchName(),
    	   allowBlank: comp.getAllowBlank()
	    });
		Ext.apply(this, {
		    setText: function(o){
		    	txtSearch.setValue(o);
		    },
		    setValue: function(o){
		    	txtCodigo.setValue(o);
		    },
		    setReadOnly: function(o){
		    	txtSearch.setReadOnly(o);
		    	txtCodigo.setReadOnly(o);
		    },
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [
		        {xtype: "label", text: comp.getFieldLabel()+":", cls: "x-form-item-label-default"},
		        {
		        	flex: 1,
		        	border: false,
			    	layout: {
			    		type: 'hbox',
			    		align: 'stretch',
			    	},
			    	items:[txtCodigo, txtSearch]
			   }
		    ]
		});
		this.callParent();
	}
});

Ext.define("admin.generics.inputs.SearchCodigoTextField", {
	extend : 'Ext.form.field.Text',
	alias: 'widget.searchcodigotextfield',
	config: {
		descripcion: null
    },
	initComponent : function() {
		var comp = this;
		Ext.apply(this, {
			emptyText: "<< >>",
			readOnly: "true",
			cls: "searchtextfield",
			listeners:{
				focus: function(f){
					if(f.readOnly) return;
					if(this.getDescripcion())
						this.nextSibling().setValue(this.getDescripcion());
					this.nextSibling().focus();
				},
				render: function(f){
					f.getEl().on('dblclick', function(){
						if(!f.readOnly) f.nextSibling().initWinGrid();
					});
				},
				change: function(){
					if(this.getValue()) this.removeCls("searchtextfield").addCls("searchtextfieldsel");
				}
			}
		});
		this.callParent();
	}
});

Ext.define("admin.generics.inputs.SearchWinTextField", {
	extend : 'Ext.form.field.Text',
	alias: 'widget.searchwintextfield',
	config: {
		url: null,
		grid: null,
		entidad: null,
		params: null
    },
	initComponent : function() {
		var comp = this;
		Ext.apply(this, {
	     	emptyText: "Buscar...",
	        enableKeyEvents: true,
	        selectOnFocus: true,
	        listeners: {
            	buffer: 500,
            	keydown: function(o, e) {
            		if(e.keyCode == 13 && comp.getUrl()){
            			comp.search();
            		}
            	},
        		blur: function(f){
        			if(f.readOnly) return;
        			if(this.previousSibling().getValue() && this.previousSibling().getDescripcion()) {
        				this.setValue(this.previousSibling().getDescripcion());
        			}
        		},
        		render: function(f){
					f.getEl().on('dblclick', function(){
						if(!f.readOnly) comp.initWinGrid();
					});
        		}
            },

            search: function(){
    			Ext.Ajax.request({
    				globalMask: true,
        		    url: comp.getUrl(),
        		    params: Ext.merge({searchtext: comp.getValue()}, comp.getParams()),
        		    success: function(response){
        		    	var data = Ext.decode(response.responseText);
        		    	if(data.data.length == 1) comp.setFields(data.data[0]);
        		    	else comp.initWinGrid();
        	    	}
        		});
            },

            setFields: function(r){
	    		comp.previousSibling().setValue(r.codigo);
	    		comp.previousSibling().setDescripcion(r.descripcion);
	    		comp.previousSibling().removeCls("searchtextfield").addCls("searchtextfieldsel");
	    		comp.setValue(r.descripcion);
            },

            setFieldsFromSelection: function(win){
            	var r = win.down("grid").view.selection;
            	if(r){
		    		comp.setFields(r.data);
					win.close();
				}
            },

        	initWinGrid: function(){
        		var grid = Ext.create('Ext.grid.Panel',{
					height: 500,
		        	scrollable: true,
		            viewConfig: {
		            	enableTextSelection: true
		            },
					store : Ext.create('Ext.data.Store', {
                		autoLoad: true,
                		listeners: {
	                		beforeload: function(s, o, e){
	    						s.proxy.extraParams = Ext.merge({searchtext: grid.down("textfield").getValue()}, comp.getParams());
	                		}
                		},
                		fields: [{name:"codigo"},{name:"descripcion"}],
                		proxy: {
                			type : 'ajax',
                			api : {
                				read: comp.getUrl()
                			},
	            			reader: {
	            				type: 'json',
	            				totalProperty: 'total',
	            				successProperty: 'success',
	            				rootProperty:'data'
	            			}
                		}
                	}),
					columns : [
						{header : 'Codigo', dataIndex : 'codigo'},
			            {header : 'Descripcion', dataIndex : 'descripcion', flex: 1}
					],
					dockedItems: [{
		                xtype: 'toolbar',
		                layout:{
		                	type: "hbox"
		                },
		                items: {
	                    	xtype: "textfield",
	                    	fieldStyle: 'color: #157fcc;',
	                    	selectOnFocus: true,
	                    	flex: 1,
	                    	value: comp.getValue(),
	                        triggers: {
	                        	clear: {
		               	             weight: 0,
		               	             cls: Ext.baseCSSPrefix + 'form-clear-trigger',
		               	             handler: function(){
		               	            	 this.setValue("");
		               	            	 grid.store.load();
		               	             }
		               	        },
	                            search: {
	                                weight: 1,
	                                cls: Ext.baseCSSPrefix + 'form-search-trigger',
	                                handler: function(){
	                                	grid.store.load();
	                                }
	                            }
	                        },
	                    	emptyText: "Buscar...",
				            enableKeyEvents: true,
					        listeners: {
				            	buffer: 500,
				            	keydown: function(o, e) {
				            		if(e.keyCode == 13) grid.store.load();
				            	}
				            }
	                    }
		            }],
		            listeners: {
		            	afterlayout: function(){
		            		this.getView().focusRow(0);
		            		this.getSelectionModel().select(0);
		            	},
						rowdblclick: function(o, r, e, ri, ev, op ){
							comp.setFields(r.data);
							win.close();
						},
				        cellkeydown: function(cell, td, cellIndex, record, tr, rowIndex, e, eOpts ) {
				            if (e.getKey() == e.ENTER) comp.setFieldsFromSelection(win);
				        }
		            }
				});

        		var win = Ext.create("Ext.window.Window", {
            		modal: true,
            		resizable: false,
            		width: 385,
            		closeAction: 'destroy',
            		title: "Selecionar "+ comp.getEntidad(),
        			layout: {
        				type: 'vbox',
        				align: 'stretch'
        			},
                    items: [grid],
        	        buttons: [
        	        {
        	            text: 'Selecionar',
        	            handler: function() {
        	            	comp.setFieldsFromSelection(win);
        	            }
        	        },{
        	            text: 'Cancelar',
        	            handler: function(){win.close();}
        	        }]
            	});
        		win.show();
        	}
	    });
		this.callParent();
		this.setTriggers({
	       	 clear: {
	             weight: 0,
	             cls: Ext.baseCSSPrefix + 'form-clear-trigger',
	             handler: function(){
	            	 this.setValue("");
	            	 this.previousSibling().setValue("");
	            	 this.previousSibling().setDescripcion("");
	            	 this.previousSibling().removeCls("searchtextfieldsel").addCls("searchtextfield");
	             }
	         },
	         search: {
	             weight: 1,
	             cls: Ext.baseCSSPrefix + 'form-search-trigger',
	             handler : function(){
	            	 this.search();
	             }
	         }
	    });
	}

}, function() {
});