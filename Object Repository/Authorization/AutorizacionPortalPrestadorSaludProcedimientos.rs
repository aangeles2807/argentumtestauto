<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Consulta los procedimientos/prestaciones bajo convenio de los planes activos del afiliado con el prestador de salud y según el servicio de salud especificados</description>
   <name>AutorizacionPortalPrestadorSaludProcedimientos</name>
   <tag></tag>
   <elementGuidId>dc11e5b0-de4d-4796-8a97-df1483e45b8c</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <katalonVersion>7.5.5</katalonVersion>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>https://app-ars-autorizaciones-qa-eastus.azurewebsites.net/api/Autorizacion/Portal/PrestadorSalud/Procedimientos?codigoUsuario=${codigoUsuario}&amp;idInteraccion=${idInteraccion}&amp;descripcion=${descripcion}</restUrl>
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
      <id>88ad9e8f-2bf4-4b76-a581-3b483d6f04c3</id>
      <masked>false</masked>
      <name>codigoUsuario</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>d907b744-4534-41b7-84de-02e4e1673d71</id>
      <masked>false</masked>
      <name>idInteraccion</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>1f15eecf-6f5e-4e7a-ba08-dab46bb8a264</id>
      <masked>false</masked>
      <name>descripcion</name>
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
