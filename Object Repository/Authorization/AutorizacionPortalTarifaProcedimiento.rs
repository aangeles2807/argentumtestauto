<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Consulta la tarifa del procedimientos/prestaciones para el proceso especificado</description>
   <name>AutorizacionPortalTarifaProcedimiento</name>
   <tag></tag>
   <elementGuidId>409c1be9-58e6-484a-8ccc-2b7b3cc3df91</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <katalonVersion>7.5.5</katalonVersion>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>https://app-ars-autorizaciones-qa-eastus.azurewebsites.net/api/Autorizacion/Portal/TarifaProcedimiento?codigoUsuario=${codigoUsuario}&amp;idInteraccion=${idInteraccion}&amp;codigoProcedimiento=${codigoProcedimiento}</restUrl>
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
      <id>d17555d9-6926-44c7-8906-fd2408dbfc14</id>
      <masked>false</masked>
      <name>codigoUsuario</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>830c67d5-c4ac-4696-bfa8-471c586fbdbc</id>
      <masked>false</masked>
      <name>idInteraccion</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>eec01e42-4a72-4eba-b43c-c78025fbe8fb</id>
      <masked>false</masked>
      <name>codigoProcedimiento</name>
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
