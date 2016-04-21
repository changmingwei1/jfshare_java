java项目列表
零、jfshare_xxx_serv.jar 项目模板

一、jfshare_ridge  统一配置管理
山脊，寓意顶梁柱，不可或缺的骨架。
使用方式：1.引入包
        <dependency>
            <groupId>com.jfshare</groupId>
            <artifactId>jfshare-ridge</artifactId>
            <version>1.0.0</version>
        </dependency>
        2.java加载启用
        <!-- zk use -->
        <bean id="configManager" class="com.jfshare.ridge.ConfigManager">
           <!--<constructor-arg name="serverList" value="127.0.0.1:2181"/>-->
        </bean>
        3.使用示例
        //代码中
        PropertiesUtil.getProperty("jfx_brand_serv", "key1", "defVal")

         @Autowired
         private ConfigManager configManager;
         configManager.getConfigValue("jfx_brand_serv", "key1", "val1");
        //spring配置中
        #{configManager.getConfigValue('jfx_public_client','brand_port')}

        #{configManager.getRedisConfig('jfx_public_redis','user_1').ip}

        #{configManager.getMysqlConfig('jfx_public_mysql','master','jfshare').url}

        ... ...

二、 jfshare_brain 服务注册发现
神经，寓意指挥官，指挥服务调用双方。
说明：ref参数依赖spring的bean实例，如需要改造成不依赖spring也不难；服务自身尽量不要引用finagle相关包，避免冲突。
使用方式：1.引入包
        <dependency>
            <groupId>com.jfshare</groupId>
            <artifactId>jfshare-brain</artifactId>
            <version>1.0.0</version>
        </dependency>
        2.1 java服务端启动方式
         <context:component-scan base-package="com.jfshare.brain"/>
          <!--brain服务端启动-->
         <bean id="brainServCfg" class="com.jfshare.brain.finagle.server.FinagleServerCfg">
             <property name="sid" value="brandServ"/> <!--此处填充服务标识-->
             <property name="ref" value="handler"/>
             <property name="api" value="com.jfshare.finagle.thrift.brand.BrandServ"/>  <!--此处填充服务thrift路径-->
             <property name="port" value="#{configManager.getConfigValue('jfx_public_client','brand_port')}"/>  <!--此处填充服务端口-->
             <property name="threads" value="300"/>  <!--此处填充服务线程数-->
             <property name="stimeout" value="80000"/>  <!--此处填充服务端超时时间(ms)-->
             <property name="version" value="1.0-SNAPSHOT"/>  <!--此处填充服务版本号->
         </bean>
         <bean id="brainServ" class="com.jfshare.brain.finagle.server.FinagleServer">
             <constructor-arg name="cfg" ref="brainServCfg"/>
         </bean>
        3.1 接口实例-固定的接口Bean名称，方便brain统一引用
           @Service(value="handler")

        2.2 客户端启动方式
           暂未实现

三、jfshare_util 公共组件
订单id、商品id生成器；工具类

四、jfshare_brand_serv 品牌服务
查询所有品牌
分页查询
批量查询

五、jfshare_subject_serv 类目服务


六、jfshare_product_serv 商品服务
查询商品明细
查询商品详情

七、jfshare-mybatis-base mybatis工具包
使用方式：1.引入包
<dependency>
    <groupId>com.jfshare</groupId>
    <artifactId>mybatis-base</artifactId>
    <version>1.0</version>
</dependency>

八、jfshare_common_serv 省市区服务

九、jfshare_address_serv 收货地址服务

十、jfshare_stock_serv 库存服务

十一、jfshare_trade_serv 交易服务

十二、trade_order_serv 订单服务



