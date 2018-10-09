Ext.define("admin.main.Main", {
	 extend: "Ext.container.Viewport",
	 xtype: 'mainview',

//    controller: 'main',
//    viewModel: 'main',

    layout: 'border',

    items: [{
    	title: "MENU",
    	xtype: "panel",
    	region: "west",
    	collapsible: true,
    	width: 400
    }]
});
