# life
一款附带多种功能的手机应用，使用okhhtp，bmob云端，百度API等
五、系统实现
（一）用户模块
1.用户登录/用户注册
登录界面：如图29，用户输入手机号码及密码并勾选协议书后点击下方按钮，验证登录成功后进入首页。
注册界面：如图30，用户输入手机号及密码并向右滑动滑块完成拼图最后勾选协议书后点击注册按钮，验证成功后进入首页。
              
图29  登录界面                            图30  注册界面
2.首页抽屉栏显示
如图31、图32所示，打开左侧抽屉栏，抽屉栏根据用户是否登录显示用户名称或游客及改变下方退出按钮文字。
                  
图31  用户登录状态抽屉栏                 图32  未登录状态抽屉栏  
3.我的账户
如图33、图34所示，点击我的账户按钮进入界面，修改按钮将该界面输入框变为可编辑状态，单击保存完成，输入框变为可读状态。
                     
图33  个人资料界面                  图34  修改个人资料界面
4.个人钱包
如图35所示，点击在左侧抽屉栏个人钱包按钮进入该界面，目前充值、提现按钮暂点击无效。

图35  个人钱包界面
5.主题管理
如图36所示，点击在左侧抽屉栏主题管理按钮进入该界面，点击对应主题单选框改变主题颜色风格。

图36  粉色主题管理界面
6.安全中心
如图37所示，点击在左侧抽屉栏安全中心按钮进入该界面。

图37  安全中心界面
（1）修改密码
如图38所示，点击修改密码按钮进入修改密码界面，输入原密码与新密码点击确认修改按钮修改完成后返回至安全中心。

图38  修改密码界面
（2）注销用户
如图39所示，点击注销用户按钮进入注销用户界面，输入密码点击去人注销按钮注销完成后返回至首页。

图39  注销账户界面
（二）首页
如图40、图41、图42所示，首页使用ViewPage2显示学习、工具组以及运动定位模块，左右滑动或单击下方按钮可切换对应页面。

图40  学习模块

图41  工具组模块                              图42  运动定位模块
（三）学习模块
1.事项/备忘录/目标/日记
如图43、图44、图45、图46所示，备忘录、目标、日记与事项界面相同。点击后显示当前登录用户的对应数据，长按弹出删除弹窗、点击弹出修改弹窗、右下角＋号弹出增加弹窗。
              
图43  事项界面                           图44  修改界面
             
图45  删除界面                            图46  增加界面
2.学习模式
                
图47  学习模式界面                      图48  学习模式计时界面
如图47所示，学习模式有正计时、倒计时功能，勾选按钮，输入目标内容和时间后点击开始即可进入如图48所示界面开始计时。
（四）工具组模块
1.进制转换/单位换算
如图49所示，进制转换：点击二进制/八进制/十进制/十六进制按钮，下方键盘对应可输入数字按钮状态为可点击状态，输入数据至上方输入框后，在对应四个进制输出框中实时输出结果。

 图49  进制转换界面
如图50、图51所示，单位换算：选择对应度量类型，选择对应输入输出单位，在输入框输入数据，实时显示结果至输出框。
             
图50  单位换算界面（一）                 图51  单位换算界面（二）
2.手机号归属地/IP/字体转换/油价
如图52、图53、图54、图55所示，手机号码归属地/IP查询/简繁火星字体转换皆输入数据点击查询按钮即可在输出框显示查询结果，而今日国内油价查询进入页面后自动查询今日国内油价显示结果至DataGridView上。
                
图52  手机号码归属地                图53  IP地址归属地
                
图54  简/繁/火星字体转换           图55  今日国内油价
3.身体加健康信息查询
如图56所示输入身体、体重、年龄、性别（不选择默认为男）、标准（不选择默认为中国），点击计算身体健康信息按钮后将查询结果显示至如图57所示结果页面，点击重新计算可返回查询界面，可重新编辑信息。
                
图56  身体健康信息查询              图57  身体健康信息查询结果
（五）运动定位模块（GPS）
1.目标
点击目标按钮弹出如图58所示目标内容的弹窗，设置时间目标或路程目标，点击对应的开始按钮即可进入运动界面。

图58  目标设置窗口界面
2.无目标
（1）运动界面
如图59所示点击开始按钮直接进入运动界面。点击暂停停止计时器并变为继续按钮，点击右下角地图图标打开如图60所示的BaiduMap界面。
           
图59  运动界面                         图60  BaiduMap界面
（2）结束页面
如图61所示，点击结束按钮结束当前运动，暂停计时器并将实时配速、时间、里程信息上传至结束页面，在结束页面点击返回按钮保存信息并返回值首页。

图61  运动结束界面
