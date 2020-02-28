<title>${entity?uncap_first}管理</title>

<div class="layui-card layadmin-header">
    <div class="layui-breadcrumb" lay-filter="breadcrumb"/>
</div>

<div class="layui-fluid">
    <div class="layui-card">
        <div class="layui-form layui-card-header layuiadmin-card-header-auto" lay-filter="layadmin-${entity?uncap_first}-formlist">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">关键字</label>
                    <div class="layui-input-block">
                        <input type="text" name="keyword" placeholder="请输入关键字" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <button class="layui-btn layuiadmin-btn-useradmin" lay-submit lay-filter="LAY-${entity?uncap_first}-front-search">
                        <i class="layui-icon layui-icon-search layuiadmin-button-btn"></i>
                    </button>
                </div>
            </div>
        </div>

        <div class="layui-card-body">
            <table id="LAY-${entity?uncap_first}-manage" lay-filter="LAY-${entity?uncap_first}-manage"></table>
            <script type="text/html" id="${entity?uncap_first}-list-manage-btn">
                <a class="layui-btn layui-btn-normal layui-btn-xs" lay-event="edit"><i
                        class="layui-icon layui-icon-edit"></i>编辑</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del"><i
                        class="layui-icon layui-icon-delete"></i>删除</a>
            </script>
            <script type="text/html" id="${entity?uncap_first}-table-manage-btn">
                <a class="layui-btn layui-btn-xs table-manage-btn" lay-event="add">创建</a>
                <a class="layui-btn layui-btn-danger layui-btn-xs table-manage-btn" lay-event="batchdel">删除</a>
            </script>
        </div>
    </div>
</div>

<script>
    layui.use('${entity?uncap_first}', layui.factory('${entity?uncap_first}')).use(['admin', 'layer', '${entity?uncap_first}', 'table'], function () {
        var $ = layui.$,
            admin = layui.admin,
            view = layui.view,
            table = layui.table,
            layer = layui.layer,
            form = layui.form;

        form.render(null, 'layadmin-${entity?uncap_first}-formlist');

        //监听搜索
        form.on('submit(LAY-${entity?uncap_first}-front-search)', function (data) {
            var field = data.field;

            //执行重载
            table.reload('LAY-${entity?uncap_first}-manage', {
                where: field
            });
        });
    });
</script>