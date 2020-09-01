<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Ingresa los datos iniciales para procesar una autorización</description>
   <name>AutorizacionPortalIngresar</name>
   <tag></tag>
   <elementGuidId>c447a24c-ea94-412f-8d2d-9b9215d07173</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent>{
  &quot;text&quot;: &quot;&quot;,
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
   <restUrl>https://app-ars-autorizaciones-qa-eastus.azurewebsites.net/api/Autorizacion/Portal/Ingresar?codigoUsuario=${codigoUsuario}&amp;idInteraccion=${idInteraccion}&amp;fecha=${fecha}&amp;codigoRemitente=${codigoRemitente}&amp;idDoctor=${idDoctor}&amp;telefono=${telefono}&amp;email=${email}&amp;codigoDiagnostico=${codigoDiagnostico}&amp;observacion=${observacion}</restUrl>
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
      <id>152bfb41-5fb8-4bf9-bbf8-a023eb2d987f</id>
      <masked>false</masked>
      <name>codigoUsuario</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>511e6676-bfca-46f8-bbfd-0c13df5c66ab</id>
      <masked>false</masked>
      <name>idInteraccion</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>ea60e2b4-f165-4749-a208-a4186c1c3f2d</id>
      <masked>false</masked>
      <name>fecha</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>7a21d266-778c-488c-a5c8-5b943a2210b1</id>
      <masked>false</masked>
      <name>codigoRemitente</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>d4985071-b079-4362-812b-efa8a2e4e297</id>
      <masked>false</masked>
      <name>idDoctor</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>8b87bf11-68cd-4d97-99be-609f35941802</id>
      <masked>false</masked>
      <name>telefono</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>4c5b866c-d281-4160-8112-cf8d045b86d4</id>
      <masked>false</masked>
      <name>email</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>ee741b39-a9e8-4897-a614-1e6312a84507</id>
      <masked>false</masked>
      <name>codigoDiagnostico</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>bcff25b1-e7c5-4fbc-8744-ee91c2adf755</id>
      <masked>false</masked>
      <name>observacion</name>
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
