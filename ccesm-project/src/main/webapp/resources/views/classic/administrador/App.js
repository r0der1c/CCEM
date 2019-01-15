Ext.define('admin.App', {
	extend: 'Ext.app.Application',
    alias: 'appccesm',
    name: 'ccesm',
    requires: ["admin.main.Main"],
   	mainView: "admin.main.Main",
//   	controllers: [],
//	init: function() {},
    launch: function () {
    	this.items && this.getMainView().add(this.items);
    }
});

