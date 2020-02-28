/** layuiAdmin.pro-v1.2.1 LPPL License By http://www.layui.com/admin/ */
;layui.define(["table", "form", "layer"], function (e) {
    var $ = layui.$, admin = layui.admin, layer = layui.layer, view = layui.view, table = layui.table,
        form = layui.form,
        token = layui.data(layui.setter.tableName)[layui.setter.request.tokenName];
    table.render({
        elem: "#LAY-studentDetailsCharts-manage",
        url: "/charts/student/details",
        headers: {'x-auth-token': token},
        request: {pageName: 'current', limitName: 'size'},
        cols: [[
            {field: "campusName", title: "校区", width: 120},
            {field: "stuNum", title: "学生总数", width: 100},
            {field: "twentyAndLower", title: "20 岁以下", width: 100},
            {field: "twentyOneToTwentyThree", title: "20-23 岁", width: 100},
            {field: "twentyFourToTwentySeven", title: "24-27 岁", width: 100},
            {field: "twentyEightToThirty", title: "28-30 岁", width: 100},
            {field: "thirtyAndAbove", title: "30 岁以上", width: 100},
            {field: "unknownAge", title: "未知年龄人数", width: 100},
            {field: "highSchoolOrLowerRate", title: "高中学历比例", width: 120, templet: function (d) {return d.highSchoolOrLowerRate + '%'}},
            {field: "collegeRate", title: "大专学历比例", width: 120, templet: function (d) {return d.collegeRate + '%'}},
            {field: "bachelorDegreeRate", title: "本科学历比例", width: 120, templet: function (d) {return d.bachelorDegreeRate + '%'}},
            {field: "masterAndAboveRate", title: "硕士学历比例", width: 120, templet: function (d) {return d.masterAndAboveRate + '%'}},
            {field: "unknownRate", title: "未知学历比例", width: 120, templet: function (d) {return d.unknownRate + '%'}},
            {field: "undergraduateRate", title: "未毕业", width: 100, templet: function (d) {return d.lessThanOneYearsRate + '%'}},
            {field: "lessThanOneYears", title: "毕业不足 1 年", width: 120, templet: function (d) {return d.oneToThreeYearsRate + '%'}},
            {field: "oneToThreeYears", title: "毕业 1-3 年", width: 125, templet: function (d) {return d.oneToThreeYearsRate + '%'}},
            {field: "fourToSevenYears", title: "毕业 4-7 年", width: 125, templet: function (d) {return d.fourToSevenYearsRate + '%'}},
            {field: "sevenYearsAndAbove", title: "毕业 7 年以上", width: 125, templet: function (d) {return d.sevenYearsAndAboveRate + '%'}},
            {field: "unknownGraduationRate", title: "未知毕业率", width: 125, templet: function (d) {return d.unknownGraduationRate + '%'}}
        ]],
        toolbar: true,
        defaultToolbar: ['filter', 'exports', {
            title: '提示' //标题
            , layEvent: 'LAYTABLE_TIPS' //事件名，用于 toolbar 事件中使用
            , icon: 'layui-icon-tips' //图标类名
        }],
        height: 'full-200',
    }), e("studentDetailsCharts", {});

});