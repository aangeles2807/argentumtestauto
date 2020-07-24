<?xml version="1.0" encoding="UTF-8"?>
<WebServiceRequestEntity>
   <description>Verifica que exista cobertura entre los planes activos del afiliado con el prestador de salud y el servicio de salud especificados</description>
   <name>AutorizacionPortalValidarCobertura</name>
   <tag></tag>
   <elementGuidId>ae4da810-5ad5-47cf-93ae-63e4aa74ed0e</elementGuidId>
   <selectorMethod>BASIC</selectorMethod>
   <useRalativeImagePath>false</useRalativeImagePath>
   <followRedirects>false</followRedirects>
   <httpBody></httpBody>
   <httpBodyContent></httpBodyContent>
   <httpBodyType></httpBodyType>
   <katalonVersion>7.5.5</katalonVersion>
   <migratedVersion>5.4.1</migratedVersion>
   <restRequestMethod>GET</restRequestMethod>
   <restUrl>https://app-ars-autorizaciones-qa-eastus.azurewebsites.net/api/Autorizacion/Portal/ValidarCobertura?codigoUsuario=${codigoUsuario}&amp;idInteraccion=${idInteraccion}&amp;codigoPrestadorSalud=${codigoPrestadorSalud}&amp;codigoSucursalPrestadorSalud=${codigoSucursalPrestadorSalud}&amp;numeroAfiliado=${numeroAfiliado}&amp;codigoServicio=${codigoServicio}</restUrl>
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
      <id>13611e88-de15-4efb-baf0-37bfbff34c65</id>
      <masked>false</masked>
      <name>codigoUsuario</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>8aafd64f-367a-41b1-b540-bba0789dbeb4</id>
      <masked>false</masked>
      <name>idInteraccion</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>6bd8dae2-b348-400b-a784-baae4f4a0629</id>
      <masked>false</masked>
      <name>codigoPrestadorSalud</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>00f81db2-81c2-42f3-aa82-d9ae7a8ef80e</id>
      <masked>false</masked>
      <name>codigoSucursalPrestadorSalud</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>58622d1c-4f23-44c6-91b4-ce250778f654</id>
      <masked>false</masked>
      <name>numeroAfiliado</name>
   </variables>
   <variables>
      <defaultValue>''</defaultValue>
      <description></description>
      <id>579c1353-1733-4bfa-8e51-bfaa5b7b3974</id>
      <masked>false</masked>
      <name>codigoServicio</name>
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
