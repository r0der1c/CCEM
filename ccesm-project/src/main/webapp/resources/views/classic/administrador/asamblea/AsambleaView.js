Ext.define("View", {
    extend: 'admin.App',
	requires:[
      'ux.GridFilterTextField',
      'admin.asamblea.AsambleaController',
      'admin.asamblea.AsambleaViewModel',
    ],
    init: function(view){
    	if(view.asambleas && view.asambleas.length){
    		Ext.each(view.asambleas, function(o){
    			view.items.items[0].items.push({
    				title: "Asamblea " + o.nombre,
    				idasm: o.idasm,
    				layout: "fit",
    				border: false,
    				defaults: {
    					border: false
    				},
    				items: {
	    			   xtype: "grid",
	    			   plugins: [
			              	Ext.create('Ext.grid.plugin.CellEditing', {
			              		clicksToEdit: 1
			              	})
			           ],
			           border: false,
	        		   defaults: {
	        			   border: false
	        		   },
	        		   tbar: {style: "margin:0;padding:0",
	        			   items:[
	        			        {xtype: "gridfiltertextfield", style: "border: none", flex: 1 },
							    {
							    	html: "+",
									handler: function(){
										this.up("grid").view.features[0].expandAll();
									}
								},
								{
									html: "-",
									handler: function(){
										this.up("grid").view.features[0].collapseAll();
									}
								},
							]
	        		   },
        			   store: {
        			        autoLoad : true,
        			        fields:[{name: "ordentsv", type: "int"}],
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
        		            },
	        		   		groupField: "ordentsv"
        			    },
        			    features: [{
	  						  ftype: "grouping",
	  						  enableNoGroups: false,
	  						  enableGroupingMenu: false,
	  						  groupHeaderTpl: '<span style="color:#5fa2dd; font-weight: bold">{[values.rows[0].data.tipo_servicio]}</span>'
  					  	}],
  					  	columns:{
			    	       defaults : {
			    	    	  menuDisabled : true,
			    	    	  sortable:false,
			    	    	  hasFocus: false
			    	       },
	    				   items:[
	    				      {xtype: 'rownumberer', width:50, flex:0, align:'center'},
    				          {header: "Servicio", flex: 1, dataIndex: "descripcion", renderer: function(v){return "<b>"+v+"</b>"}},
    				          {header: "Servidor", flex: 1, dataIndex: "servidor", editor: {type:"textfield",selectOnFocus: true}},
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
    		   tbar: {style: "margin:0;padding:0", items:[{xtype: "gridfiltertextfield", style: "width:100%;border: none" }]},
    		   bind:{
    			   store:"{servidores}"
    		   },
	    	   columns:{
	    		   items:[
    		          {xtype: 'rownumberer', width:50, flex:0, align:'center'},
    		          {header: "Servidor", dataIndex: "nombre_listado", flex:1},
		          ]
	    	   }
	       }
       ]
    }
});
