Ext.define("View", {
    extend: 'admin.App',
	requires:[
      'admin.asamblea.AsambleaController',
      'admin.asamblea.AsambleaViewModel'
    ],
    init: function(view){
    	if(view.asambleas && view.asambleas.length){
    		Ext.each(view.asambleas, function(o){
    			view.items.items[0].items.push({
    				title: "Asamblea " + o.nombre,
    				idasm: o.idasm,
    				layout: "fit",
    				defaults: {
    					border: false
    				},
    				items: {
	    			   xtype: "grid",
	        		   defaults: {
	        			   border: false
	        		   },
        			   store: {
        			        autoLoad : true,
        		            proxy: {
        		                type: 'ajax',
        		                extraParams:{
        		                	idasm: o.idasm,
        		                },
        		                api: {
        		                	read: "getServiciosAsamblea",
        		                },
        		                reader: {
        		                    type: 'json',
        		                    rootProperty: 'list'
        		                },

        		            }
        			    },
	    			   columns:{
	    				   items:[
	    				      {xtype: 'rownumberer', width:50, flex:0, align:'center'},
    				          {header: "Servicio", flex: 1, dataIndex: "codigo"},
    				          {header: "Servidor", flex: 1, dataIndex: "descripcion"},
				          ]
	    			   }
	    		   }
    			});
    		});
    	}
    },
    items: {
    	controller: "asambleacontroller",
    	viewModel: "asambleaviewmodel",
    	region: "center",
        layout: {
            type: 'hbox',
            align: 'stretch',
        },
    	defaults: {
    		flex: 1,
    		border: false
    	},
    	items:[
    	   {
    		   xtype: "tabpanel",
    		   tabPosition: "bottom",
    		   defaults: {
    			   border: false
    		   },
    		   items:[],
    		   listeners: {
    			   tabchange: "tabchange"
    		   }
    	   },
	       {
	    	   xtype: "grid",
	    	   columns:{
	    		   items:[
    		          {header: "Servidor"},
		          ]
	    	   }
	       }
       ]
    }
});
