<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Procesa los procedimientos/prestaciones indicados</description>
   <name>AutorizacionPortalAutorizar</name>
   <tag></tag>
   <elementGuidId>710df730-abc8-4972-90a5-5de236c42494</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;[\n  {\n    \&quot;Codigo\&quot;: \&quot;${Codigo}\&quot;,\n    \&quot;Habitacion\&quot;: \&quot;\&quot;,\n    \&quot;Cantidad\&quot;: ${Cantidad},\n    \&quot;Valor\&quot;: ${Valor},\n    \&quot;ValorConcesion\&quot;: 0,\n    \&quot;ValorExcepcion\&quot;: 0\n  }\n]&quot;,
  &quot;contentType&quot;: &quot;application/json&quot;,
  &quot;charset&quot;: &quot;UTF-8&quot;
}</httpBodyContent>
   <httpBodyType>text</httpBodyType>
   <httpHeaderProperties>
      <isSelected>true</isSelected>
      <matchCondition>equals</matchCondition>
      <name>Content-Type</name>
      <type>Main</type>
      <value>application/json</value>
   </httpHeaderProperties>
   <katalonVersion>7.5.5</katalonVersion>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>POST</restRequestMethod>
   <restUrl>https://app-ars-autorizaciones-qa-eastus.azurewebsites.net/api/Autorizacion/Portal/Autorizar?codigoUsuario=${codigoUsuario}&amp;idInteraccion=${idInteraccion}</restUrl>
   <serviceType>RESTful</serviceType>
   <soapBody></soapBody>
   <soapHeader></soapHeader>
   <soapRequestMethod></soapRequestMethod>
   <soapServiceEndpoint></soapServiceEndpoint>
   <soapServiceFunction></soapServiceFunction>
   <useServiceInfoFromWsdl>true</useServiceInfoFromWsdl>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>a3ee68a3-26e7-4026-aaa4-c11df87b41f7</id>
      <masked>false</masked>
      <name>codigoUsuario</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>72a2b13a-61b0-417d-a2aa-b9e23b2cea52</id>
      <masked>false</masked>
      <name>idInteraccion</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>f9a28d5a-3013-4f0c-8a62-f112b83a22ad</id>
      <masked>false</masked>
      <name>Codigo</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>589835da-2605-4ee3-a403-0c349056c60d</id>
      <masked>false</masked>
      <name>Valor</name>
   </variables>
   <variables>
      <defaultValue>1</defaultValue>
      <description></description>
      <id>8ca2c06d-4683-4d0b-b77d-471cff21811d</id>
      <masked>false</masked>
      <name>Cantidad</name>
   </variables>
   <verificationScript>import static org.assertj.core.api.Assertions.*

import com.kms.katalon.core.testobject.RequestObject
import com.kms.katalon.core.testobject.ResponseObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webservice.verification.WSResponseManager

import groovy.json.JsonSlurper
import internal.GlobalVariable as GlobalVariable

RequestObject request = WSResponseManager.getInstance().getCurrentRequest()

ResponseObject response = WSResponseManager.getInstance().getCurrentResponse()</verificationScript>
   <wsdlAddress></wsdlAddress>
</WebServiceRequestEntity>
