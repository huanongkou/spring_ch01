**14:59 2016/9/11 by Dakou**
>**现在的学习都是碎片化，学的不是很深入，现在决定重新学习Spring系列，先从基础学起，慢慢扩展。**   
#这个小项目主要用Spring MVC搭建一个web项目
>**目的：深刻理解handlermapping,view,model,viewResolver,messageCovert,@RequestMapping,@RespondBody,对静态资源的访问配置**

**所用组件：**
>maven：maven管理包，jetty 插件   
>Spring MVC -4.2.5.RELEASE

知识点：

1. Spring MVC的核心是DispatcherServlet,这个Servlet充当Spring MVC的前端控制器。与其他servlet一样，同样在web.xml中生命。

web.xml的配置
```xml
	<!-- spring context -->
	<context-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>classpath:spring/application-config.xml</param-value>
	</context-param>
	<listener>
		<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
	</listener>
	<!-- Spring MVC -->
	<servlet>
		<servlet-name>dispatcherServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param>
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/mvc-config.xml</param-value>
		</init-param>
		<load-on-startup>1</load-on-startup>
	</servlet> 	
	<servlet-mapping>
		<servlet-name>dispatcherServlet</servlet-name>
		<!-- 通过将DispatchcerServlert映射到/，声明了它会作为默认的servlet并且会处理所有的请求，包括对静态资源的请求。 -->
		<url-pattern>/</url-pattern>
	</servlet-mapping>
	<!-- 编码过滤器 -->
	<filter>
		<filter-name>encodingFilter</filter-name>
		<filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
		<async-supported>true</async-supported>
		<init-param>
			<param-name>encoding</param-name>
			<param-value>UTF-8</param-value>
		</init-param>
	</filter>
	<filter-mapping>
		<filter-name>encodingFilter</filter-name>
		<url-pattern>/*</url-pattern>
	</filter-mapping>
```
mvc.xml的配置
```xml
<!-- 处理对静态资源的请求 -->
	<mvc:resources mapping="/static/**" location="/static/,/images/" />
	<!-- <mvc:default-servlet-handler/> -->
	<!-- 包扫描路径，便于容器查找@Controller -->
	<context:component-scan base-package="org.dakou.controller" />
	<!-- 定义handlermapping（dispatchServlet利用它定位请求应该被哪个controller处理） 使用DefaultAnnotationHandlerMapping 
		注解驱动，使用@RequestMapping -->
	<mvc:annotation-driven>
		<mvc:message-converters>
			<bean class="org.springframework.http.converter.StringHttpMessageConverter" />
			<bean
				class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter" />
		</mvc:message-converters>
	</mvc:annotation-driven>
	<!-- 视图解释器 -->
	<bean
		class="org.springframework.web.servlet.view.InternalResourceViewResolver">
		<property name="prefix" value="/WEB-INF/view/" />
		<property name="suffix" value=".jsp" />
	</bean>
```

1. Spring MVC的处理流程   
1.1 浏览器发送一个请求，这个请求首先到达DispatcherServlet,DispatcherServlet会查询一个或多个处理器映射handlermapping（使用DefaultAnnotationHandlerMapping，用<mvc:annotion-driver/>驱动来声明）来确定请求的下一站到哪，根据请求携带的参数确定被哪个controller处理。   
1.2. 确定了controller后，就等待控制器处理信息（委托给一个或多个service），一般控制器会产生一些信息，并返回，在浏览器展现给用户，这些信息成为模型（Model）,这些信息发给一个视图（view）,通常是JSP。  
1.3. 控制器所做的最后一件事是将模型数据打包，并且标识出渲染输出的视图名称，然后将请求连同模型和视图名称发给DispatcherServlet，结束。   
1.4. 控制器不会与特定的视图相耦合，传递给DispatcherServlet的视图名不能直接表示某个特定的JSP，实际上，甚至不能确定视图就是JSP，仅仅是一个逻辑名，DispatcherServlet将使用视图解释器viewResolver来匹配一个特定的视图实现，有可能jsp，freemarker等等。

2. 配置Spring MVC对静态资源的访问
由于将DispatcherServlet映射到/，声明了他会作为默认的servlet并且处理所有的请求，包括静态资源。所有经过DispatcherServlet的请求都必须按照一定得方式来进行处理，通常情况下需要用controller控制器来处理。但是没有必要为了请求静态资源编写和维护一个控制器，以下是解决方式。
>方法一：在mvc.xml中配置`<mvc:resources mapping="/static/**" location="/static/,/images/" />`，所有的静态资源：js、css、image都要放在webapp/static/*（http://localhost:9999/spring_ch01/static/images/1.jpg）、webapp/images/（直接访问http://localhost:9999/spring_ch01/static/2.jpg）下,注意路径是相对路径。补充，可以有多个<mvc:resources/>
>方法二：之所有无法访问静态资源，因为没有对应的controller来指向静态资源，那么就用容器默认的DefaultServletHandler处理 所有静态内容与无RequestMapping处理的URL，加上这个默认的servlet时候，servlet在找不到的时候会去找静态的内容。
在mvc.xml中配置`<mvc:default-servlet-handler/>`  
这个系列的代码用的方法一   
3. 支持JSON或XML格式
由于handler处理完后会返回model和view给viewrResolver，所以，controller控制器会返回视图，但是有时需要接收或返回json或者xml给前端，这时就需要MessageConvert，Spring提供了多种Message-convertre,在handlermapping配置。

```xml
<mvc:annotation-driven>
		<mvc:message-converters>
			<bean class="org.springframework.http.converter.StringHttpMessageConverter" />
			<bean
				class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter" />
		</mvc:message-converters>
	</mvc:annotation-driven>
```	
4:返回JSON或者XML代替view
控制器Controller的处理方法使用@ResponseBody注解完全绕过视图解释，并使用信息转换器将返回值转换给客户端。
