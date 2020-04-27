/*
* create at 2019-12-26
* 本JS整理一些在工作中经常使用的JS工具方法
* @Auth xiaoshaDestiny
* */


// 1、
// 获取两个日期之前的日期数组    参数是都是10为的字符串   yyyy-MM-dd
function getBetweenDateStr(start,end){
    var result = [];
    var beginDay = start.split("-");
    var endDay = end.split("-");
    var diffDay = new Date();
    var dateList = new Array;
    var i = 0;
    diffDay.setDate(beginDay[2]);
    diffDay.setMonth(beginDay[1]-1);
    diffDay.setFullYear(beginDay[0]);
    result.push(start);
    while(i == 0){
        var countDay = diffDay.getTime() + 24 * 60 * 60 * 1000;
        diffDay.setTime(countDay);
        dateList[2] = diffDay.getDate();
        dateList[1] = diffDay.getMonth() + 1;
        dateList[0] = diffDay.getFullYear();
        if( String(dateList[1]).length == 1){dateList[1] = "0"+dateList[1]};
        if( String(dateList[2]).length == 1){dateList[2] = "0"+dateList[2]};
        result.push(dateList[0]+"-"+dateList[1]+"-"+dateList[2]);
        if(dateList[0] == endDay[0] && dateList[1] == endDay[1] && dateList[2] == endDay[2]){ i = 1;
        }
    }
    return result;
}


//2、
// 工具方法 转换日期   date类型 转为 8位字符串
function parseDate(date) {
    var str = "";
    str += date.getFullYear();
    var mm = date.getMonth() + 1
    if(date.getMonth() > 9){
        str += mm;
    }
    else{
        str += "0" + mm;
    }
    if(date.getDate() > 9){
        str += date.getDate();
    }
    else{
        str += "0" + date.getDate();
    }
    return str;
}



//3、
// 工具方法 转换日期   date类型 转为 xxxx年xx月xx日
function parseDateWithNYR(date) {
    var str = "" + date.getFullYear();
    str += "年";
    var mm = date.getMonth()+1;
    if(date.getMonth() > 9){
        str += mm;
    }
    else{
        str += "0" + mm;
    }
    str += "月";
    if(date.getDate() > 9){
        str += date.getDate();
    }
    else{
        str += "0" + date.getDate();
    }
    str += "日";
    return str;
}



//4、
// 工具方法 将yyyyMMdd 转化为yyyy-MM-dd
function date8To10(value) {
    return value.substring(0, 4) + "-" + value.substring(4, 6) + "-" + value.substring(6, 8);
}


//5、
// 工具方法  将yyyy-MM-dd转化为yyyyMMdd
function date10To8(value) {
    return value.substring(0, 4) + value.substring(5, 7) + value.substring(8, 10);
}


//6、
// 正数  小数  检查规则   只允许输入正数，可以是小数
function positiveNumberCheck(value){
    value = value.replace(/[^\d,.]/g,""); //清除"数字"和"."以外的字符
    value = value.replace(/^\./g,""); //验证第一个字符是数字而不是"."
    value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的"."
    value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//只允许输入一个小数点
    value = value.replace(/^[0]+[0-9]*$/gi,"0");//不能是0开头,输入了0 下一个不能仔输入数字，只能输入"."
    return value;
}
//允许输入正数负数  可以输小数
function numberCheck(value){
    value = value.replace(/[^\d.-]/g,""); //清除"数字"和"."以外的字符
    value = value.replace(/^\./g,""); //验证第一个字符是数字而不是"."
    value = value.replace(/\.{2,}/g,"."); //只保留第一个. 清除多余的"."
    value = value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");//只允许输入一个小数点
    value = value.replace(/^[0]+[0-9]*$/gi,"0");//不能是0开头,输入了0 下一个不能仔输入数字，只能输入"."
    value = value.replace(/^\-+\./g,"-");
    return value;
}

//正整数规则检查
function positiveIntegerRuleCheck(value){
    value = value.replace(/[^\d]/g,"");//去掉所有不是数字的输入
    value = value.replace(/^[0]+[0-9]*$/gi,"");//去掉是0开头的输入
    return value;
}


//7、
// layui 单击行选中radio
$('body').on('click', '.layui-table-body tr', function (e) {
    $('.layui-form-radio', this).click();
});
$('body').on('click', '.layui-form-radio', function (e) {
    e.stopPropagation();
});


//8、
// layui 单击行勾选checkbox事件 这一串代码不用改动就能实现功能
$(document).on("click",".layui-table-body table.layui-table tbody tr", function () {
    var index = $(this).attr('data-index');
    var tableBox = $(this).parents('.layui-table-box');
    //存在固定列
    if (tableBox.find(".layui-table-fixed.layui-table-fixed-l").length>0) {
        tableDiv = tableBox.find(".layui-table-fixed.layui-table-fixed-l");
    } else {
        tableDiv = tableBox.find(".layui-table-body.layui-table-main");
    }
    var checkCell = tableDiv.find("tr[data-index=" + index + "]").find("td div.laytable-cell-checkbox div.layui-form-checkbox I");
    if (checkCell.length>0) {
        checkCell.click();
    }
});
$(document).on("click", "td div.laytable-cell-checkbox div.layui-form-checkbox", function (e) {
    e.stopPropagation();
});


//10
//做了一个功能，layui的table的一行数据，其中一列的值变了，另一列的值也跟着变动
//下拉选的lay-filter设置成 samplingCd  改变的列是第3列
layui.use('form', function () {
    var form = layui.form;
    form.on('select(samplingCd)', function(data){
        data.othis.parent().parent().parent().parent().children()[2].children[0].innerHTML = "";
        for (var i = 0; i < allData.length; i++) {
            if(allData[i].samplingCd == data.elem.id){
                allData[i].edit = true;
                allData[i].schemeId = data.value;
                var pcStr = "1次/";
                for (var j = 0; j < allData[i].schemeList.length; j++) {
                    if(allData[i].schemeList[j].id == data.value){
                        allData[i].interval = allData[i].schemeList[j].interval;
                        pcStr = pcStr + allData[i].schemeList[j].interval + "h";
                        data.othis.parent().parent().parent().parent().children()[2].children[0].append(pcStr);
                    }
                }
            }
        }
    });
});


//11
//表格折叠 Start
//表格折叠  网上找来的 原文链接：
// https://fly.layui.com/jie/62437/
// https://gitee.com/hzxun/layerTable
/**
 * 面板扩展表格 2.x
 * date:2020-01-05
 * License By CrazyYi.
 */
layui.define(['table', 'laytpl'], function (exports) {
    var $ = layui.jquery;
    var table = layui.table;
    var laytpl = layui.laytpl;
    // 展开/折叠 状态码
    var eventExpand = '1'; // 展开
    var eventClosed = '2'; // 折叠

    /**
     * 默认参数，语法定义，参考了layui.table 的options 的方式
     * reference: https://www.layui.com/doc/modules/table.html#options
     */
    var defaultOption = {
        mainElem: undefined,   // main table 容器
        url: undefined,  // http API接口
        method: 'post', // http 请求的方法
        data: [],  // 数据
        cols: [],  // 列配置，二位数组

        where: undefined, // 异步加载数据的请求参数的名称，同时也是html 中action 方法中后面带的参数，注意要保持顺序一致

        // 【重要！！重要！！重要！！】
        // reqField：可以是对象，也可是数组。v2.0新增
        // 如果是对象，索引是url http 请求的参数名。值是行记录的参数字段名。
        reqField: undefined,

        // 【重要！！重要！！重要！！】
        rowData: undefined,     // 当前要操作行的所有记录，要和 reqField 字段配合使用，获取需要异步传递的参数，v2.0新增
        eventObj: undefined,    // 整张表格渲染的数据，v2.0新增，暂时没用
        keyField: undefined, // 整张表索引的名称，v2.0新增
        jQueryThis: undefined, // 一般用于layui.table中的on监听tool的时候，=$(this)，v2.0新增
        jQueryTr: undefined, // tr DOM，=$(this).parent('tr')，v2.0新增
        width: undefined,  // 容器宽度
        height: undefined,  // 容器高度
        cellMinWidth: 100,  // 单元格最小宽度
        totalRow: false, // 是否开启 合计
        skin: 'line', // line =（行边框风格），row =（列边框风格），nob =（无边框风格）
        size: 'sm',  // 表格尺寸，默认=sm
        even: true,  // 是否开启隔行变色，默认=true
        evenColor: "#C8EFD4", // 隔行变色的底色，默认=#C8EFD4
        parseData: function (res) { return parseData(this, res); },
        renderTable: function (that, res, curr, count) { return renderTable(this, that, res, curr, count); }, // 完成后的回调，处理 table
        renderEvenRow: function (that, res) { return renderEvenRow(this, that, res); }, // 完成后的回调，处理 table even row

        layEventOn: true, // 是否开启 lay-event 触发事件，默认：开启
        layEvent: eventExpand, // 1=展开，2=折叠，支持任意格式

        // 【重要！！重要！！重要！！】
        // 这里是配置 展开/折叠 操作的属性，包括 事件，名称，icon 等
        layEvents: {
            expand: {func: 'closed', title: '', icon: 'layui-icon-triangle-d'}, // 展开事件
            closed: {func: 'expand', title: '', icon: 'layui-icon-triangle-r'}, // 折叠事件
        }, // 展开、折叠 配置
        logOn: false, // 是否开启 log 打印
    };

    /**
     * Sub Table 默认参数，这里所有的参数，都遵循着 layui table 的规定
     * reference: https://www.layui.com/doc/modules/table.html#options
     */
    var subDefaultOptions = {
        elem: undefined,
        url: undefined,
        data: undefined,
        method: undefined,
        where: undefined, // 要传递的数据
        even: false, // 隔行背景
        size: undefined, // 尺寸，sm=小尺寸，lg=大尺寸
        skin: undefined, // line =（行边框风格），row =（列边框风格），nob =（无边框风格）
        height: undefined,
        width: undefined,
        cols: undefined,
        parseData: function (res, curr, count) { },
        done: function (res, curr, count) { },
        totalRow: false,
        cellMinWidth: undefined,
    };

    /**
     * layerTable 类构造方法
     * @param options
     * @param event
     */
    var layerTable = function (options, event) {
        event ? options.layEvent = event : ''; // 采用这种方式也可以渲染
        this.options = $.extend(defaultOption, options);

        this.init();
    };

    /**
     * 初始化
     */
    layerTable.prototype.init = function () {
        // 初始化：main table
        this.mainTableInit(); // 当前主表

        // 初始化：where对象
        var options = this.options;
        var where = {};
        var regex = /^\d+$/; //判断字符串是否为数字
        for (var i in options.reqField) {
            var param = options.reqField[i];
            // 如果 索引i是数字
            if (regex.test(i)) {
                where[param] = options.rowData[param];
            } else {
                where[i] = options.rowData[param];
            }
        }
        this.options.where = where;

        this.printLog('options:', this.options);
        this.printLog('mainTable:', this.mainTable);

        // 开始执行
        $(this).attr('lay-event', 'closed1').html('<i class="layui-icon layui-icon-triangle-d"></i>折叠1');
        switch (this.options.layEvent) {
            case eventExpand:
                this.expand();
                break;
            case eventClosed:
                this.closed();
                break;
            default:
                this.printLog('layEvent error:', this.options.layEvent);
        }
    };

    /**
     * 新增一行，在行内新增一个表格
     * @returns {boolean}
     */
    layerTable.prototype.insertTable = function () {
        this.printLog('--- insert table');
        var options = this.options;
        var tr = options.jQueryTr;
        var mainTb = this.mainTable;
        var id = mainTb.iRow;
        var tableId = this.newTableId(id); // 新增表格的id
        var trId = this.newTrId(id);
        var _html = [
            '<tr class="table-item" id="' + trId + '">', // 新增一行，并给该行新增一个id属性
            '  <td colspan="' + mainTb.tdCount + '" style="padding: 6px 12px;">', // 合并整行所有单元格
            '    <table class="layui-table layui-hide" id="' + tableId + '"></table>', // 在单元格内新增一个表格
            '  </td>',
            '</tr>'
        ];
        tr.after(_html.join('\n'));  // 在当前行的后面，插入一行
        return true;
    };

    /**
     * 删除新增的表格（删除该表格所在的新行）
     * @returns {boolean}
     */
    layerTable.prototype.devareTable = function () {
        this.printLog('--- devare table');
        var tr = this.options.jQueryTr;
        tr.next().remove();
        return true;
    };

    /**
     * 折叠（直接把这行删除即可）
     */
    layerTable.prototype.closed = function () {
        this.eventChange('closed');
        this.devareTable();
    };

    /**
     * 详情（新增一行，并在行内创建一个新表格）
     */
    layerTable.prototype.expand = function () {
        this.eventChange('expand');
        this.insertTable();
        this.newTableRender();
    };

    /**
     * 改变 toolbar 或 tempvar 单元格的内容，样式等
     * @param action
     */
    layerTable.prototype.eventChange = function (action) {
        var options = this.options;
        if (!options.layEventOn) return; // 没有开启 lay-event 更新

        var jqThis = options.jQueryThis;
        var eventCfg = options.layEvents[action];
        var _html = '<i class="layui-icon currIcon"></i>currTitle';
        _html = _html.replace('currIcon', eventCfg.icon);
        _html = _html.replace('currTitle', eventCfg.title);
        jqThis.attr('lay-event', eventCfg.func).html(_html);
    };

    /**
     * 新增子表的重新渲染
     */
    layerTable.prototype.newTableRender = function () {
        this.printLog('--- table render');
        var options = this.options;
        var subOptions = {
            elem: '#' + this.newTableId(this.mainTable.iRow),
            url: options.url,
            data: options.data,
            method: options.method,
            where: options.where, // 要传递的数据,
            even: options.even, // 隔行背景,
            size: options.size,
            height: options.height,
            width: options.width,
            cols: options.cols,
            parseData: function (res) {
                if (options.parseData) {
                    return options.parseData(res); // 类似 table 的parseData
                }
            },
            done: function (res, curr, count) {
                var that = this.elem.next(); // 这个是获取到整个新建表格的上层元素

                if (options.renderHeader) {
                    options.renderHeader(that, res); // 设置表格头header样式
                }
                if (options.renderEvenRow) {
                    options.renderEvenRow(that, res); // 设置奇数行的底色
                }
                if (options.renderTable) {
                    options.renderTable(that, res, curr, count);
                }
            }
        };
        this.subOptions = $.extend(subDefaultOptions, subOptions);
        // 表格 渲染
        var subTable = table.render(this.subOptions);
    };

    /**
     * 新增行（用于显示详情）的id
     * @param id
     * @returns {string}
     */
    layerTable.prototype.newTrId = function (id) {
        return 'trSubInfo' + id;
    };

    /**
     * 新增表的id
     * @param id
     * @returns {string}
     */
    layerTable.prototype.newTableId = function (id) {
        return "tbSubInfo" + id;
    };

    /**
     * 初始化主表 main Table
     */
    layerTable.prototype.mainTableInit = function () {
        var options = this.options;
        var elem = options.mainElem;
        var elemId = (elem.substr(0, 1) === '#') ? elem.substr(1) : elem;
        var tb = document.getElementById(elemId);

        var iRow = this.currRow(); // 获取当前 id 对应的行

        var tr = options.jQueryTr; // $(this).parent('tr')
        var tdCount = tr.find('td').length;  // 每一行的单元格的数量（就是列的数量）
        // 保存 表相关参数
        this.mainTable = {
            table: tb,
            tdCount: tdCount,
            iRow: iRow
        };
    };

    /**
     * 获取当前 操作的行 在 tb 表中对应的行，下标从0开始计算
     * @returns {number}
     */
    layerTable.prototype.currRow = function () {
        var options = this.options;
        var tr = options.jQueryTr;
        var trIndex = tr.data('index');
        this.printLog('curr row:', trIndex);
        return trIndex;
    };

    /**
     * 打印Log
     * @param msg
     * @param data
     * @returns {boolean}
     */
    layerTable.prototype.printLog = function (msg, data) {
        if (!this.options.logOn) return false;
        if (data) {
            console.log('layerTable | ' + msg, data);
        } else {
            console.log('layerTable | ' + msg);
        }
        return true;
    };

    /**
     * 数据格式解析的回调函数，用于将返回的任意数据格式解析成 table 组件规定的数据格式
     * @param options
     * @param res
     * @returns {*}
     */
    var parseData = function (options, res) {
        return res;
    };

    /**
     * 设置奇数行的底色
     * @param options
     * @param that
     * @param res
     */
    var renderEvenRow = function (options, that, res) {
        if (options.even) {
            res.data.forEach(function (item, index) {
                if (index % 2 == 0) {
                    var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", options.evenColor);
                } else {
                    // var tr = that.find(".layui-table-box tbody tr[data-index='" + index + "']").css("background-color", "#CCCCFF");
                }
            });
        }
    };

    /**
     * done之后的回调，设置 表格
     * @param options
     * @param that
     * @param res
     */
    var renderTable = function (options, that, res) {

    };

    /**************************************************
     * 外部调用
     */

    /**
     * 供外部调用的成员
     *
     * @type {{render: (function(*=): layerTable)}}
     */
    var layerTB;
    layerTB = {
        render: function (options, event) {
            return new layerTable(options, event);
        },
        getOptions: function () {
            return this.options;
        }
    };
    exports('layerTable', layerTB);
});
//表格折叠 End


//12
//layui 时间选择 第二次渲染无效解决方案
//思路是 copy一个相同的时间输入
function loadTime(min,max) {
    layui.use('laydate', function(){
        var laydate = layui.laydate;
        laydate.render({
            elem: '#plan-sampling-time',
            type: 'time',
            format: 'HH:mm',
            min: min,
            max: max,
            btns: ['clear', 'confirm']
        });
    });
}
function destroyTime() {
    $("#plan-sampling-time").each(function (index, elemTemp) {
        elemTemp = $(elemTemp);
        var key = elemTemp.attr('lay-key');
        if (key) {
            // 如果打开着就给关掉
            $('#layui-laydate' + key).remove();
        }
        // copy 当前的节点
        var nodeClone = elemTemp.clone(true);
        // 替换节点 去掉lay-key方便后面重新render
        elemTemp.replaceWith(nodeClone.attr('lay-key', null));
    });
}
//end

//13
//表格 分页 复选框 过滤  start
layui.define(['jquery', 'table'], function (exports) {
    var $ = layui.jquery, table = layui.table;
    //记录选中表格记录编号
    var checkedList = {};
    var tableCheckBoxUtil = {
        //初始化分页设置
        init: function (settings) {
            checkedList[settings.gridId] = settings.ids;
            var param = {
                gridId: '',//表格id
                filterId: '',//表格lay-filter值
                fieldName: '' //表格主键字段名
            };
            $.extend(param, settings);

            //设置当前保存数据参数
            if (checkedList[param.gridId] == null) {
                checkedList[param.gridId] = [];
            }

            //监听选中行
            table.on('checkbox(' + param.filterId + ')', function (obj) {
                var type = obj.type;
                var checked = obj.checked;
                console.log(table);

                //当前页数据
                var currentPageData = table.cache[param.gridId];
                //当前选中数据
                var checkRowData = [];
                //当前取消选中的数据
                var cacelCheckedRowData = [];

                //debugger;
                //选中
                if (checked) {
                    checkRowData = table.checkStatus(param.gridId).data;
                }
                //取消选中
                else {
                    if (type == 'all') {
                        cacelCheckedRowData = currentPageData;
                    }
                    else {
                        cacelCheckedRowData.push(obj.data);
                    }
                    //console.log(cacelCheckedRowData);
                }
                //debugger;
                //清除数据
                $.each(cacelCheckedRowData, function (index, item) {
                    var itemValue = item[param.fieldName];

                    checkedList[param.gridId] = checkedList[param.gridId].filter(function (fItem, fIndex) {
                        return fItem != itemValue;
                    })
                });

                //添加选中数据
                $.each(checkRowData, function (index, item) {
                    var itemValue = item[param.fieldName];
                    if (checkedList[param.gridId].indexOf(itemValue) < 0) {
                        checkedList[param.gridId].push(itemValue);
                    }
                });
            });
        },
        //设置页面默认选中（在表格加载完成之后调用）
        checkedDefault: function (settings) {
            var param = {
                gridId: '',
                fieldName: ''
            };

            $.extend(param, settings);

            //当前页数据
            var currentPageData = table.cache[param.gridId];
            if (checkedList[param.gridId] != null && checkedList[param.gridId].length > 0) {
                $.each(currentPageData, function (index, item) {
                    var itemValue = item[param.fieldName];

                    if (checkedList[param.gridId].indexOf(itemValue) >= 0) {
                        //设置选中状态
                        item.LAY_CHECKED = true;

                        var rowIndex = item['LAY_TABLE_INDEX'];
                        updateRowCheckStatus(param.gridId, 'tr[data-index=' + rowIndex + '] input[type="checkbox"]');
                    }
                });
            }
            //判断当前页是否全选
            var currentPageCheckedAll = table.checkStatus(param.gridId).isAll;
            if (currentPageCheckedAll) {
                updateRowCheckStatus(param.gridId, 'thead tr input[type="checkbox"]');
            }
        },
        //获取当前获取的所有集合值,
        getValue: function (settings) {
            var param = {
                gridId: '' //表格id
            };
            $.extend(param, settings);

            return checkedList[param.gridId];
        },
        //设置选中的id（一般在编辑时候调用初始化选中值）
        setIds: function (settings) {
            var param = {
                gridId: '',
                ids: []//数据集合
            };
            $.extend(param, settings);
            checkedList[param.gridId] = [];
            $.each(param.ids, function (index, item) {
                checkedList[param.gridId].push(parseInt(item));
            });
        }
    };

    var updateRowCheckStatus = function (gridId, ele) {
        var layTableView = $('.layui-table-view');
        //一个页面多个表格，这里防止更新表格错误
        $.each(layTableView, function (index, item) {
            if ($(item).attr('lay-id') == gridId) {
                $(item).find(ele).prop('checked', true);
                $(item).find(ele).next().addClass('layui-form-checked');
            }
        });
    }
    //输出
    exports('tableCheckBoxUtil', tableCheckBoxUtil);
});
//表格 分页 复选框 过滤  end
/**
 * 使用
 *
 *
 <!--第1步：引入js-->
 <script src="../layui.table.utils.js"></script>

 //第2步：获取js对象
 var tableCheckBoxUtil = layui.tableCheckBoxUtil;

function renderTeamTable() {
    layui.use(['table'], function () {
        table = layui.table;
        tableCheckBoxUtil = layui.tableCheckBoxUtil;

        tableIns = table.render({
            elem: '#rm-auth-person-add-person',
            url: zuulUrl + "/base/peopleCertification/pageWithParam",
            headers: {"_token": _token},
            defaultToolbar: false,
            page: true,
            cols: [[
                {type: 'checkbox', fixed: 'left'},
                {field: 'userName', title: '用户', fixed: 'left'},
                {field: 'accountName', title: '账号',fixed: 'left'},
                {field: 'userId', title: '账号',fixed: 'left'},
            ]],
            id: 'rm-auth-person-add-person',
            parseData: function (res) {
                return {
                };
            },
            done: function (res) {
                //第4步：初始化表格行选中状态 表格加载完成之后调用
                tableCheckBoxUtil.checkedDefault({
                    gridId: 'rm-auth-person-add-person',
                    fieldName: 'userId'
                });

                //第5步：获取值
                //翻页之后，存一下数据  返回的是id的数组
                personIds = tableCheckBoxUtil.getValue({gridId: 'rm-auth-person-add-person'});

            }
        });

        //第3步：初始化分页设置
        //gridId 表格定义的id，在table.render里面定义的
        //filterId 标签上的过滤id
        //fieldName 这个表格数据的主键
        //ids:数据库里面的  需要反显的id List
        tableCheckBoxUtil.init({
            gridId: 'rm-auth-person-add-person',
            filterId: 'rm-auth-person-add-person',
            fieldName: 'userId',
            ids:personIds
        });
    });
}

//保存
function addAuthPerson() {
    //第6步：在保存之前获取勾选上的id数组
    personIds = tableCheckBoxUtil.getValue({gridId: 'rm-auth-person-add-person'});
}

 *
 *
 *
 *
 */



