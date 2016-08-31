namespace java com.jfshare.finagle.thrift.message

include "result.thrift"

/* 系统消息 */
struct SystemMessage{
    1:i32 id,
    2:optional string title,
    3:optional string content,
    4:optional string beginDate,
    5:optional string endDate,
    /*1:未开始  2:进行中  3:已结束*/
    6:optional i32 status,
    7:optional i32 pushTarget,
    8:optional string createTime,
    /* 消息类型。0:系统消息， 1:订单消息， 2:url类 */
    9:optional i32 msgType
    10:string alert
}

struct SystemMessageResult{
    1:result.Result result,
    2:optional list<SystemMessage> messages
}

struct PushMessage{
    1:string title,
    2:string content,
    /* 消息类型。0:系统消息， 1:订单消息， 2:url类 */
    3:i32 objType,
    4:string alert
    
}

struct AppUpgradeInfo{
    /* 类型：1,android buyer  2,android seller  3, ios*/
    1:i32 appType,
    /* 升级到客户端版本号 */
    2:i32 version,
    /* 需要更新的最小版本号*/
    3:i32 minVersion,
    /* 需要更新的最大版本号*/
    4:i32 maxVersion,
    /* 升级类型 1:普通升级   2: 强制升级*/
    5:i32 upgradeType,
    /* 客户端地址 */
    6:string url,
    /* 升级描述 */
    7:string upgradeDesc
}

struct AppUpgradeInfoStr{
    /* 类型：1,android buyer  2,android seller  3, ios*/
    1:i32 appType,
    /* 升级到客户端版本号 */
    2:string version,
    /* 需要更新的最小版本号*/
    3:string minVersion,
    /* 需要更新的最大版本号*/
    4:string maxVersion,
    /* 升级类型 1:普通升级   2: 强制升级*/
    5:i32 upgradeType,
    /* 客户端地址 */
    6:string url,
    /* 升级描述 */
    7:string upgradeDesc
}

struct GetUpgradeParam{
    /* 类型：1,android buyer  2,android seller  3, ios */
    1:i32 appType,
    /* 当前客户端版本号 */
    2:i32 version
}

struct GetUpgradeParamStr{
    /* 类型：1,android buyer  2,android seller  3, ios */
    1:i32 appType,
    /* 当前客户端版本号 */
    2:string version
}

struct AppUpgradeResult{
    /* 应答 */ 
    1:result.Result result,
    /* */
    2:AppUpgradeInfo upgradeInfo
}

struct AppUpgradeResultStr{
    /* 应答 */ 
    1:result.Result result,
    /* */
    2:AppUpgradeInfoStr upgradeInfo
}

service MessageServ{
    result.Result addSystemMessage(1:SystemMessage message);
    SystemMessageResult getSystemMessage(1:SystemMessage message);
    result.Result deleteSystemMessage(1:i32 id);
    result.Result updateSystemMessage(1:SystemMessage message);
    result.Result sendPush(1:PushMessage message);
    
    /* 查询升级信息 */
    AppUpgradeResult getAppUpgradeInfo(1:GetUpgradeParam param);
    /* 添加客户端升级信息 */
    result.Result addAppUpgradeInfo(1:AppUpgradeInfo info);
    /* 更新客户端升级信息 */
    result.Result updateAppUpgradeInfo(1:AppUpgradeInfo info);
    
    /* ---------新版-查询升级信息
     /* 查询升级信息 */
    AppUpgradeResultStr getAppUpgradeInfoStr(1:GetUpgradeParamStr param);
    
    /*推送消息by sdk*/
    /*orderType 10:待支付 30:待发货 40:待收货 50:已完成*/
    result.Result pushMessageInfo(1:string userId, 2:PushMessage message, 3:optional string orderType);
}
