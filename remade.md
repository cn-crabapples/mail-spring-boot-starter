> # mail-spring-boot-starter

### 快速配置邮件发送模块

- 引入maven坐标
  ```xml
  <dependency>
    <groupId>cn.crabapples</groupId>
    <artifactId>mail-spring-boot-starter</artifactId>
    <version>1.0.0</version>
  </dependency>
  ```
- 填写相关配置(application.properties)
  ```properties
  #邮件发送服务器地址(string)
  crabapples.host=xxx
  #发件人邮箱(string)
  crabapples.source=xxx
  #邮件发送服务器端口(int)
  crabapples.port=xxx
  #发件人账号(string)
  crabapples.username=xxx
  #发件人账号(string)
  crabapples.password=xxx
  #是否需要授权(boolean,default false)
  crabapples.auth=xxx
  #是否开启debug(boolean,default false)
  crabapples.debug=xxx
  ```

  ```
  @Autowired MailService mailService
  Multipart multipart = new MultipartBuilder().addContent().build();
  String [] target = {"xxxx@qq.com"};
  boolean status = mailService.sendEmail(multipart,"title",target);
  ```
