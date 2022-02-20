var ajaxMethods = {

    load: function (loadItem, url, parameters, callback){
        $(loadItem).load(url,parameters,callback);
    }
}