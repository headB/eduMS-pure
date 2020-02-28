/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(function (e) {
    var i = (layui.layer, layui.laytpl, layui.setter, layui.view, layui.admin),
        admin = layui.admin,
        $ = layui.$,
        c = {};
    c.genYesOrNoTag = function (bol, yesText, noText) {
        return bol ? "<span style='color: red;'>" + (yesText ? yesText : '是') + "</span>" : "<span style='color: green;'>" + (noText ? noText : '否') + "</span>"
    },
    c.renderSelector = function (form, cache, url, eleSelector, formlist, valueAttr, nameAttr) {
        if (!cache) {
            // 渲染 select
            admin.req({
                url: url,
                done: function (res) {
                    if (res.data && res.data.length) {
                        var options = '';
                        $.each(res.data, function (i, e) {
                            options += "<option value='" + e[valueAttr] + "'>" + e[nameAttr] + "</option>";
                        });
                        cache = options;
                        $(eleSelector).append(options);
                        form.render("select", formlist)
                    }
                }
            });
        } else {
            $(eleSelector).append(cache);
            form.render("select", formlist);
        }
    },
    i.events.logout = function () {
        i.req({
            url: "/logout", type: "post", data: {}, done: function (e) {
                i.exit()
            }
        })
    }, e("common", c)
});