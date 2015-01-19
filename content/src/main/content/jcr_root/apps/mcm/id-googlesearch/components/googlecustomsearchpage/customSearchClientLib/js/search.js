CQ.Ext.namespace("CQ.google.restAPI");
CQ.google.restAPI = {

    SERVLET_PATH: "/bin/custom/searchConfig",

    loadContentHandler: function (dialog) {
        CQ.Ext.Ajax.request({
            url: CQ.google.restAPI.SERVLET_PATH,
            params: {
                "apikey": dialog.getField("./apikey").getValue(),
                "appname": dialog.getField("./appname").getValue(),
                "cx": dialog.getField("./cx").getValue()
            },
            method: "GET",
            success: function (response, options) {
                var json = CQ.Ext.util.JSON.decode(response.responseText);

                if (json['success']) {
                    dialog.getField("./isConnected").setValue(true);
                    CQ.cloudservices.getEditOk().enable();
                }
            }
        });
    },

    connect: function (dialog) {
        CQ.Ext.Ajax.request({
            url: CQ.google.restAPI.SERVLET_PATH,
            params: {
                "apikey": dialog.getField("./apikey").getValue(),
                "appname": dialog.getField("./appname").getValue(),
                "cx": dialog.getField("./cx").getValue()
            },
            method: "GET",
            success: function (response, options) {
                var json = CQ.Ext.util.JSON.decode(response.responseText);

                if (json['error']) {
                    CQ.Ext.Msg.show({
                        title: CQ.I18n.getMessage("Error"),
                        buttons: CQ.Ext.Msg.OK,
                        msg: CQ.I18n.getMessage("Error in Connecting with Google custom search Service"),
                        icon: CQ.Ext.MessageBox.ERROR
                    });
                    return;
                }

                if (json['success']) {
                    dialog.getField("./isConnected").setValue(true);

                    CQ.Ext.Msg.show({
                        title: CQ.I18n.getMessage("Success"),
                        msg: CQ.I18n.getMessage("Connected with Google Custom Search API successfully, please save the configuration"),
                        icon: CQ.Ext.MessageBox.INFO,
                        buttons: {
                            ok: CQ.I18n.getMessage("Ok")
                        },
                        fn: function (buttonId) {
                            if (buttonId === "ok") {
                                // Enable the OK Button
                                CQ.cloudservices.getEditOk().enable();
                            }
                        }
                    });
                }
            },
            failure: function (response, options) {
                CQ.Ext.Msg.show({
                    title: CQ.I18n.getMessage("Error"),
                    buttons: CQ.Ext.Msg.OK,
                    msg: CQ.I18n.getMessage("Error in Connecting with Google custom search Service"),
                    icon: CQ.Ext.MessageBox.ERROR
                });
            }
        });
    }
}	