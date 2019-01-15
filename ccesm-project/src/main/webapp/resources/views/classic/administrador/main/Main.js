Ext.define("admin.main.Main", {
	 extend: "Ext.container.Viewport",
	 xtype: 'mainview',
	 requires:['admin.main.MainController'],
	 controller: 'maincontroller',
//    viewModel: 'main',
    layout: 'border',
    defaults: {
    	border: false
    },
    items: [
        {
        	title: "GRUPO EMANUEL",
        	region: "north",
        },
        {
	    	title: "MENU",
	    	xtype: "treepanel",
	    	region: "west",
	    	collapsible: true,
	    	collapsed: true,
	    	width: 400,
	    	rootVisible: false,
	    	store:{
	    	    root: {
	    	        expanded: true,
	    	        children: [
	    	            {id: 1, text: 'ADMINISTRAR ASAMBLEA', leaf: true }
    	            ]
	    	    }
	    	},
	    	listeners: {
	    		selectionchange: "selectionchangeMenu"
	    	}
        }
    ]
});
