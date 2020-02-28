/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer","common","xmSelect","upload"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        common = layui.common,
        upload = layui.upload,
        xmSelect = layui.xmSelect,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-studentMockInterview-manage",
        url: "/studentMockInterviews",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[ //表头
            {type: "checkbox", fixed: "left"}
            ,{field: "studentName", title: "学员"}
            ,{field: "subjectName", title: "学科"}
            ,{field: "classinfoName", title: "班级",width:200}
            ,{field: 'interviewTime', title: '面试时间'}
            ,{field: 'stage', title: '面试阶段'}
            ,{field: 'content', title: '面试内容',hide:true}
            ,{field: 'expressionScore', title: '表达分数',hide:true}
            ,{field: 'basicScore', title: '基础知识分数',hide:true}
            ,{field: 'distributionScore', title: '分布式分数',hide:true}
            ,{field: 'projectScore', title: '项目熟悉分数',hide:true}
            ,{field: 'compositeScore', title: '综合分数'}
            ,{field: 'canPass', title: '是否通过', templet: function (row) {
                    return common.genYesOrNoTag(!row.canPass,'否' , '是');
                }}
            ,{field: 'teacher', title: '面试老师'}
            ,{field: 'teacherComment', title: '老师点评',hide:true}
            ,{title: "操作", width: 150, align: "center", fixed: "right", toolbar: "#studentMockInterview-list-manage-btn"}
        ]],
        toolbar: '#studentMockInterview-table-manage-btn',
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        page: true,
        limit: 30,
        height: 'full-200',
    }), table.on("toolbar(LAY-studentMockInterview-manage)", function (e) {
        active[e.event](e);
    }), table.on("tool(LAY-studentMockInterview-manage)", function (e) {
        active[e.event](e);
    }), e("studentMockInterview", {});

    //事件
    var active = {
        "batchdel": function (e) {
            var checkStatus = table.checkStatus('LAY-studentMockInterview-manage')
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
                    url: '/studentMockInterviews',
                    type: 'delete',
                    data: {ids: ids},
                    done: function (e) {
                        layer.msg(e.msg);
                            if (e.code === 0) {
                                setTimeout(function () {
                                    layui.table.reload("LAY-studentMockInterview-manage"), layer.close(index)
                                }, 1000);
                            }
                    }
                });
            });
        }, "add": function (e) {
            admin.popup({
                title: '添加',
                area: ['1100px', '650px'],
                id: 'LAY-popup-studentMockInterview-add',
                success: function (layero, i) {
                    view(this.id).render('teaching/studentMockInterview/form').done(function () {
                        initForm(null,i);
                    });
                }
            });
        }, "edit": function (e) {
            var data = e.data;

            admin.popup({
                title: "编辑",
                area: ['1100px', '650px'],
                id: "LAY-popup-studentMockInterview-add",
                success: function (e, i) {
                    view(this.id).render("teaching/studentMockInterview/form", data).done(function () {
                        initForm(data,i);
                    });
                }
            });
        }, "del": function(e) {
            layer.confirm("确定要删除该数据吗？", function (i) {
                if (e.data.id) {
                    admin.req({
                        url: '/studentMockInterviews',
                        data: {ids: [e.data.id]},
                        type: 'delete',
                        done: function (res) {
                            layer.msg(res.msg);
                        }
                    });
                }
                e.del(), layer.close(i)
            });
        },
        "import":function (e) {
            admin.popup({
                title: "导入",
                area: ["500px", "450px"],
                id: "LAY-popup-studentMockInterview-import",
                success: function (e, i) {
                    view(this.id).render("teaching/studentMockInterview/import").done(function () {
                        var index;
                        //上传文件
                        upload.render({
                            elem: '#choose'
                            ,url: '/studentMockInterviews?type=excel'
                            ,auto: false
                            ,accept: 'file'
                            ,acceptMime:'application/vnd.ms-excel'
                            ,bindAction: '#upload'
                            ,headers: {'x-auth-token': token}
                            ,before:function () {
                                index=layer.msg('数据导入中', {
                                    icon: 16,
                                    time : false,
                                    shade: 0.01
                                });
                            }
                            ,done: function (e) {
                                layer.close(index);
                                if (e.code === 0) {
                                    layer.msg(e.msg);
                                    setTimeout(function () {
                                        layui.table.reload("LAY-studentMockInterview-manage"), layer.close(i)
                                    }, 1000);
                                }else{
                                    layer.msg(e.msg, {
                                        icon: 2,
                                        time : 5000,
                                        shade: 0.01
                                    });
                                }
                            }
                        });
                    })
                }
            });
        }
    };


    function initForm(data,i){
        $("#f_subjectId").html(subjects);
        $("#f_classinfoId").html(classinfos);
        var option = [];
        var type = "post";
        if(data){
            option = [{
                name:data.studentName,
                value:data.studentId,
                selected:true
            }];
            type = "put";
            // 数据回显
            form.val("layadmin-studentMockInterview-formlist",data);
        }

        //学员下拉框
        xmSelect.render({
            el: '#f_studentId',
            filterable: true,
            remoteSearch: true,
            radio: true,
            clickClose: true,
            name: 'studentId',
            model: {
                label: {
                    type: 'text',
                    text: {
                        //左边拼接的字符
                        left: '',
                        //右边拼接的字符
                        right: '',
                        //中间的分隔符
                        separator: ', ',
                    },
                }
            },
            data:option,
            remoteMethod: function(val, cb, show){ //远程模糊查询
                //判断是否中文
                var pattern = new RegExp("[\u4E00-\u9FA5]+");
                if(pattern.test(val)){
                    admin.req({
                        url: '/students?size=-1&trueName=' + val,
                        done: function (res) {
                            var options = ''
                            if (res.data && res.data.length) {
                                var arr = [];
                                $.each(res.data, function (i, e) {
                                    arr[i] = {
                                        name:e.trueName,
                                        value : e.id
                                    }
                                });
                                cb(arr)
                            }
                        }
                    });
                }else{
                    cb([])
                }

            }
        })
        // 重新渲染页面
        form.render(null, "layadmin-studentMockInterview-formlist");

        // 绑定提交表单事件
        form.on("submit(LAY-studentMockInterview-front-submit)", function (e) {
            // 提交数据到后台
            admin.req({
                url: '/studentMockInterviews',
                type: type,
                data: e.field,
                done: function (e) {
                    layer.msg(e.msg);
                    if (e.code === 0) {
                        setTimeout(function () {
                            layui.table.reload("LAY-studentMockInterview-manage"), layer.close(i)
                        }, 1000);
                    }
                }
            });
            return false;
        });
    }
});