/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-estimateCustom-manage",
        url: "/estimateCustoms",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {type: "checkbox", fixed: "left"},
            {field: "description", title: "模板描述"},
            {field: "typeTitle", title: "评分类型"},
            {field: "subjectName", title: "学科类型"},
            {field: "truename", title: "创建人"},
            {title: "操作", width: 270, align: "center", fixed: "right", toolbar: "#estimateCustom-list-manage-btn"}
        ]],
        toolbar: '#estimateCustom-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-estimateCustom-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-estimateCustom-manage)", function (e) {
        active[e.event](e);
    }), e("estimateCustom", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-estimateCustom-manage')
            , checkData = checkStatus.data; //得到选中的数据

            if (checkData.length === 0) {
                return layer.msg('请选择数据');
            }

            layer.confirm('确定删除吗？', function (index) {
                var ids = $.map(checkData, function (v, i) {
                    return v.id
                });
                //执行 Ajax 后重载
                admin.req({
                    url: '/estimateCustoms',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-estimateCustom-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        },
        "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['500px', '650px'],
                id: 'LAY-popup-estimateCustom-add',
                success: function (layero, index) {
                    view(this.id).render('teaching/estimateCustom/form').done(function () {
                        form.render(null, 'layadmin-estimateCustom-formlist');

                        //监听提交
                        form.on('submit(LAY-estimateCustom-front-submit)', function (data) {
                            // 提交数据到后台
                            admin.req({
                                url: '/estimateCustoms',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-estimateCustom-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            return false;
                        });
                    });
                }
            });
        },
        "addTemplate": function (e) {




            window.currentTemplateDescription = e.data.description;
            window.currentTemplateSubjectName = e.data.subjectName;
            window.currentTemplateTypeTitle = e.data.typeTitle;
            window.currentCustomId = e.data.id;
            admin.popup({
                title: '添加评分问题',
                area: ['980px', '650px'],
                id: 'LAY-popup-estimateCustom-addTemplate',
                success: function (layero, index) {
                    view(this.id).render('teaching/estimateCustom/addTemplateForm').done(function () {
                        form.render(null, 'layadmin-estimateCustom-formlistTemplate');

                        //当点击出发成功之后，去获取一些数据，并且填充到表单上，列出已经拥有的问题列表
                        admin.req({
                            url: '/estimateCombinations/' + window.currentCustomId,
                            type: 'get',
                            done: function (e) {
                               //能成功获取到数据的话，就进行字段的渲染
                                window.currentContents = [];
                                $.each(e.data,function (i,e1) {

                                    if(e1.contentId != null){
                                        window.currentContents.push(e1);
                                    }

                                });


                            }

                        });




                        //监听提交
                        form.on('submit(LAY-estimateCustom-front-submit)', function (data) {




                            //先检查是否都是有数据先，如果没有的话，直接返回就算了。！


                            if (!data.field.id || data.field.id== ""){
                                alert("找不到模板ID");
                                return false;
                            }

                            if (Object.keys(data.field).length == 1){
                                alert("没有输入正确数量的问题");
                                return false;
                            }

                            //遍历所有字段，并且里面所有的数值不能为空
                            var returnFalse = false;
                            $.each(data.field,function (i,e) {



                                if (!i.endsWith("id")  && e == ""){
                                    alert("有问题出现空输入情况，请检查确保所有的选项都是有数值！");
                                    returnFalse = true;
                                    return false;
                                }

                            });

                            if (returnFalse){
                                return false;
                            }


                            //把需要批量删除的ids添加进字段里面
                            data.field.ids = window.ids;

                            // 提交数据到后台
                            admin.req({
                                url: '/estimateCustoms/createCombination',
                                type: 'post',
                                data: data.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                            layui.table.reload("LAY-estimateCustom-manage"), layer.close(index)
                                        }, 1000);
                                    }
                                }
                            });

                            //利用其他提交其他的删除操作，试试不同在后台那边写删除规则。
                            if (window.ids.length >0){
                                admin.req({
                                    url: '/estimateCombinations',
                                    type: 'delete',
                                    data: {ids:window.ids},
                                    done: function (e) {
                                        window.ids = [];
                                    }

                                });
                            }

                            return false;
                        });
                    });
                }
            });
        },
        "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ['960px', '480px'],
                id: "LAY-popup-estimateCustom-add",
                success: function (e, i) {
                    view(this.id).render("teaching/estimateCustom/form", data).done(function () {

                        // 数据回显
                        console.log(data);
                        form.val("layadmin-estimateCustom-formlist", data);

                        // 重新渲染页面
                        form.render(null, "layadmin-estimateCustom-formlist");
                        // 绑定提交表单事件
                        form.on("submit(LAY-estimateCustom-front-submit)", function (e) {
                            // 提交数据到后台
                            admin.req({
                                url: '/estimateCustoms',
                                type: 'put',
                                data: e.field,
                                done: function (e) {
                                    layer.msg(e.msg);
                                    if (e.code === 0) {
                                        setTimeout(function () {
                                        layui.table.reload("LAY-estimateCustom-manage"), layer.close(i)
                                    }, 1000);
                                }
                                }
                            });
                            return false;
                        });
                    });
                }
            });

            // setTimeout(function () {
            //     // 为什么我的表单是如何多多灾多难的！！！终于搞定了。添加了延时加载！
            //     form.val("layadmin-estimateCustom-formlist", data);
            // },100)

        },
        "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/estimateCustoms',
                        data: {ids: [e.data.id]},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                        }
                    });
                }
                e.del(), layer.close(i)
            });
        }
    };
});