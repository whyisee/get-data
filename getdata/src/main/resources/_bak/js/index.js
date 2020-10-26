var main_flow_id=""
var source_flow_id=""
var troop_flow_id=""
var cond_flow_id=""
var show_flow_id=""
var exec_flow_id=""
var data_flow_id=""

var dev_data1=""
var dev_data2=""
var dev_data3=""
var dev_data4=""
var dev_data5=""

function test() {
    console.log("测试!")

}

function getDataSource() {
    var url="/getdata/datasource"
    var data=""
    var change="getDataSource"
    var type="GET"


    testinterface(url,data,change,type)


}

function choiceDataSource() {
    var url="/getdata/datasource"
    var data=""
    var change="choiceDataSource"
    var type="GET"

    var flow_id=testinterfacesync("/getdata/common/seq",data,change,type);
    main_flow_id=flow_id
    source_flow_id=flow_id

    //flowValue1 选择的数据源编码
    //flowValue2 选择的数据源名称
    //flowValue3 选择的数据源中文名称
    //flowValue4 选择的数据源别名
    var choiceDataSource = document.getElementById(change).value;
    data={"flowId":flow_id,"flowName":"选择数据源","flowKey":"data_source","flowValue1":choiceDataSource,"parentFlowId":main_flow_id}
    testinterfacesync("/getdata/configflow",data,change,"POST")
    //console.log("id:"+testinterfacesync("/getdata/common/seq",data,change,type));
    dev_data1=choiceDataSource;

}

function addUserTroop(){
    var url="/getdata/usertroop"
    var data=""
    var change="getDataSource"
    var type="GET"
    var common_id=testinterfacesync("/getdata/common/seq",data,change,type);

    data={"troopId":common_id,"troopName":"测试上传用户群","tagFlowId":common_id,"flowValue1":"1000000002"}
    //增加用户群
    testinterfacesync(url,data,change,"POST")
    //增加用户群标签
    data={"tagId":common_id,"tagName":"test","tagNameZh":"测试标签","tagFromId":common_id,"flowValue1":"1000000002"}
    testinterfacesync("/getdata/tagconfigflow",data,change,"POST")


}

function getUserTroop() {
    var url="/getdata/usertroop"
    var data=""
    var change="getUserTroop"
    var type="GET"
    testinterface(url,data,change,type)

}

function choiceUserTroop() {
    var url="/getdata/usertroop"
    var data=""
    var change="choiceUserTroop"
    var type="GET"

    //flowValue1 选择的用户群
    //flowValue2 选择的用户群交集并集方式
    //flowValue3 排除的用户群
    //flowValue4 排除的用户群交集并集方式
    var choiceDataSource = document.getElementById(change).value;
    var flow_id=testinterfacesync("/getdata/common/seq",data,change,type);
    troop_flow_id=flow_id
    data={"flowId":flow_id,"flowName":"选择客户群","flowKey":"use_troop_id"
        ,"flowValue1":choiceDataSource,"flowValue2":"1","parentFlowId":main_flow_id}
    testinterfacesync("/getdata/configflow",data,change,"POST")
    //console.log("id:"+testinterfacesync("/getdata/common/seq",data,change,type));
}

function getCondition() {
    var url="/getdata/tagconfig"
    var data=""
    var change="getCondition"
    var type="GET"
    var choiceDataSource = document.getElementById("choiceDataSource").value;
    //data={"tagFromId":choiceDataSource}
    testinterface(url,data,change,type)
    testinterface(url+"flow",data,change+"flow",type)

}

function choiceCondition() {
    var url="/getdata/tagconfig"
    var data=""
    var change="choiceCondition"
    var type="GET"

    //flowValue1 选择的条件
    //flowValue2 排除的条件
    //flowValue3 前台构造和解析使用的json串
    //
    var choiceDataSource = document.getElementById(change).value;
    var flow_id=testinterfacesync("/getdata/common/seq",data,change,type);
    cond_flow_id=flow_id
    data={"flowId":flow_id,"flowName":"配置筛选条件","flowKey":"condition"
        ,"flowValue1":choiceDataSource,"flowValue2":"1","parentFlowId":main_flow_id}
    testinterfacesync("/getdata/configflow",data,change,"POST")
    //console.log("id:"+testinterfacesync("/getdata/common/seq",data,change,type));
    dev_data2=choiceDataSource;

}

function getShowTag() {
    var url="/getdata/tagconfig"
    var data=""
    var change="getShowTag"
    var type="GET"
    var choiceDataSource = document.getElementById("choiceDataSource").value;
    //data={"tagFromId":choiceDataSource}
    testinterface(url,data,change,type)
    testinterface(url+"flow",data,change+"flow",type)

}

function choiceShowTag() {
    var url="/getdata/tagconfig"
    var data=""
    var change="choiceShowTag"
    var type="GET"

    var choiceDataSource = document.getElementById(change).value;
    //flowValue1 选择的标签编码
    //flowValue2 选择的标签名称
    //flowValue3 选择的标签中文名称
    //flowValue4 选择的标签配置
    var flow_id=testinterfacesync("/getdata/common/seq",data,change,type);
    show_flow_id=flow_id
    data={"flowId":flow_id,"flowName":"选择展示指标","flowKey":"show_tag"
        ,"flowValue1":choiceDataSource,"flowValue2":"1","parentFlowId":main_flow_id}
    testinterfacesync("/getdata/configflow",data,change,"POST")
    //console.log("id:"+testinterfacesync("/getdata/common/seq",data,change,type));
    dev_data3=choiceDataSource;

}
function choiceExecPlan() {
    var url="/getdata/tagconfig"
    var data=""
    var change="choiceShowTag"
    var type="GET"

    var choiceDataSource = document.getElementById(change).value;
    //flowValue1 执行引擎
    //flowValue2 执行周期类型
    //flowValue3 选定日期
    //flowValue4 执行sql
    var flow_id=testinterfacesync("/getdata/common/seq",data,change,type);
    exec_flow_id=flow_id
    data={"flowId":flow_id,"flowName":"配置执行计划","flowKey":"show_tag"
        ,"flowValue1":"localmysql","flowValue2":"W","flowValue3":"1,5","parentFlowId":main_flow_id}
    testinterfacesync("/getdata/configflow",data,change,"POST")
    //console.log("id:"+testinterfacesync("/getdata/common/seq",data,change,type));
}

function saveGD() {
    var url="/getdata/configmain"
    var data=""
    var change="getDataSaveInfo"
    var type="GET"

    var choiceDataSource = document.getElementById(change).value;
    //flowValue1 文件格式
    //flowValue2 文件分隔符
    //flowValue3 是否生成客户群
    //flowValue4 结果保存方式
    var flow_id=testinterfacesync("/getdata/common/seq",data,change,type);
    data_flow_id=flow_id
    data={"flowId":flow_id,"flowName":"选择结果保存方式","flowKey":"show_tag"
        ,"flowValue1":"csv","flowValue2":",","flowValue3":"1|20201201","flowValue4":"1","parentFlowId":main_flow_id}
    testinterfacesync("/getdata/configflow",data,change,"POST")


    data={"taskId":main_flow_id,"taskName":choiceDataSource,"taskStatus":"1",
    "sourceFlowId":source_flow_id,"troopFlowId":troop_flow_id,"condFlowId":cond_flow_id
        ,"showFlowId":show_flow_id,"execFlowId":exec_flow_id,"dataFlowId":data_flow_id
    ,"execSql":" select "+dev_data3+" from "+dev_data1 + " where 1=1 and " + dev_data2}
    testinterfacesync(url,data,change,"POST")

    //console.log("id:"+testinterfacesync("/getdata/common/seq",data,change,type));
}

function listGD() {
    var url="/getdata/configmain"
    var data=""
    var change="listGD"
    var type="GET"
    testinterface(url,data,change,type)

}
function getResultView(){
    var url="/getdata/common/getresultdev"
    var data=""
    var change="resultView"
    var type="GET"
    var choiceDataSource = document.getElementById("getResultView").value;
    url=url+"/"+choiceDataSource
    //data={"taskId":choiceDataSource}
    testinterface2(url,data,change,type)
}

function testinterface(url,data,change,type){
//loginName:"333",
    var testdata={loginName:"333",userName:"test",studentId:"123123",
        sex:"男",inDate:"2019",college:"计算机",specially:"软件"
    }
    var testdata2=JSON.stringify(testdata);
    console.log(data)
    $.ajax({
        url:url, //请求的url地址
        contentType: 'application/json;charset=UTF-8',
        dataType:"json", //返回格式为json
        async:true,//请求是否异步，默认为异步，这也是ajax重要特性
        data:JSON.stringify(data), //参数值
        type:type, //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(req){
            document.getElementById(change).innerHTML  = syntaxHighlight(req.data.list);
            console.log(req.data)
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
            document.getElementById(change).value="111";
            console.log(1)

        }
    });

}

function testinterface2(url,data,change,type){
//loginName:"333",
    var testdata={loginName:"333",userName:"test",studentId:"123123",
        sex:"男",inDate:"2019",college:"计算机",specially:"软件"
    }
    var testdata2=JSON.stringify(testdata);
    console.log(data)
    $.ajax({
        url:url, //请求的url地址
        contentType: 'application/json;charset=UTF-8',
        dataType:"json", //返回格式为json
        async:true,//请求是否异步，默认为异步，这也是ajax重要特性
        data:JSON.stringify(data), //参数值
        type:type, //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(req){
            document.getElementById(change).innerHTML  = syntaxHighlight(req);
            console.log(req.data)
        },
        complete:function(){
            //请求完成的处理
        },
        error:function(){
            //请求出错处理
            document.getElementById(change).value="111";
            console.log(1)

        }
    });

}



function testinterfacesync(url,data,change,type){
//loginName:"333",
    var testdata={loginName:"333",userName:"test",studentId:"123123",
        sex:"男",inDate:"2019",college:"计算机",specially:"软件"
    }
    var testdata2=JSON.stringify(testdata);
    var result=""

    console.log(data)
    $.ajax({
        url:url, //请求的url地址
        contentType: 'application/json;charset=UTF-8',
        dataType:"json", //返回格式为json
        async:false,//请求是否异步，默认为异步，这也是ajax重要特性
        data:JSON.stringify(data), //参数值
        type:type, //请求方式
        beforeSend:function(){
            //请求前的处理
        },
        success:function(req){
            //document.getElementById(change).innerHTML  = JSON.stringify(req);
            //return(req.data)
            result=req.data;
        },
        complete:function(){
            //请求完成的处理
           // return req
        },
        error:function(){
            //请求出错处理
            document.getElementById(change).value="111";
            console.log(1)

        }
    });
    //console.log(123+result)
    return result;

}

function syntaxHighlight(json) {
    if (typeof json != 'string') {
        json = JSON.stringify(json, undefined, 2);
    }
    json = json.replace(/&/g, '&').replace(/</g, '<').replace(/>/g, '>');
    return json.replace(/("(\\u[a-zA-Z0-9]{4}|\\[^u]|[^\\"])*"(\s*:)?|\b(true|false|null)\b|-?\d+(?:\.\d*)?(?:[eE][+\-]?\d+)?)/g, function(match) {
        var cls = 'number';
        if (/^"/.test(match)) {
            if (/:$/.test(match)) {
                cls = 'key';
            } else {
                cls = 'string';
            }
        } else if (/true|false/.test(match)) {
            cls = 'boolean';
        } else if (/null/.test(match)) {
            cls = 'null';
        }
        return '<span class="' + cls + '">' + match + '</span>';
    });
}