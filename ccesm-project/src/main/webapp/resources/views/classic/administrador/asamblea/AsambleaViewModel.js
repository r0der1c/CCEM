Ext.define('admin.asamblea.AsambleaViewModel', {
    extend : 'Ext.app.ViewModel',
    alias : 'viewmodel.asambleaviewmodel',
    stores : {
	    servidores : {
	        autoLoad : true,
            proxy: {
                type: 'ajax',
                api: {
                	read: "getServidores",
                },
                reader: {
                    type: 'json',
                    rootProperty: 'list'
                },

            }
	    }
    }
});
