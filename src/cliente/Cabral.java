package cliente;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;


public class Cabral
{
	//private static final String URL_PROVIDER = "http://10.192.72.174:8080/";
		private static final String URL_PROVIDER = "http://localhost:8085/";
		private static final String SERVICE_URL = URL_PROVIDER + "v1/unidadesOrganizacionais/consultalistapaa";
		private static final String RESPONSE_PARAM_UND_PAA = "undPAA";
		private static final String HEADER_CONTENT_TYPE = "Content-type";
		private static final String ACCESS_TOKEN = "cb2d708e-b679-4fcc-a30e-c879f53b9579";
		private static final int VALUE_PESSOA_JURIDICA = 2269651;
		private static final String HEADER_AUTHORIZATION = "access-token";
		private static final String PARAM_UND_ORGNZ_ENTRADA = "undOrgnzEntrada";
		private static final String PARAM_TIPO_PESQUISA = "tipoPesquisa";
		private static final String PARAM_PESSOA_JURIDICA = "pessoaJuridica";	

		public List<Long> obterPostosAtendimentoPorCodigoAgencia(String agenciaProdutora) throws Exception
		{
					
			List<Long> codigos = new ArrayList<Long>();
			
			try {
				//Instancia cliente
				HttpClient httpClient = new HttpClient();
				//Faz um get na url
				HttpMethod method = new GetMethod(SERVICE_URL);
				
				//Cria uma lista de pares de valors (key, value)
				List <NameValuePair> nvps = new ArrayList <NameValuePair>();
				//Add os pares de valores na lista
				nvps.add(new NameValuePair(PARAM_PESSOA_JURIDICA, String.valueOf(VALUE_PESSOA_JURIDICA)));
				nvps.add(new NameValuePair(PARAM_TIPO_PESQUISA, String.valueOf(2)));
				nvps.add(new NameValuePair(PARAM_UND_ORGNZ_ENTRADA, agenciaProdutora));
				
				//Joga os valores da lista em um array como query string
				NameValuePair[] para = new NameValuePair[]{};
				//Monta a parte da url com os parametros
				method.setQueryString(nvps.toArray(para));
				
				//Add header (token e content type)
				method.addRequestHeader(HEADER_AUTHORIZATION, ACCESS_TOKEN);
				method.addRequestHeader(HEADER_CONTENT_TYPE, "application/json");
				
				//Se http status diferente de 200(ok), algo errado. lanã exception
				int statusCode = httpClient.executeMethod(method);
				if(statusCode != 200){
					throw new Exception("Erro " + statusCode + "Response: " + method.getResponseBodyAsString());				
				}	
				
				// Joga o retorno da requisicao (json) para a string
				String json = method.getResponseBodyAsString();
				//tira os espaços que ficaram
				json = json.replace(" ", "");
				
				//Identifica dentro da string json so a parte do undPAA e joga no strBuildField
				Pattern p = Pattern.compile("\""+RESPONSE_PARAM_UND_PAA+"\":(.*?)(?:,)");
				Matcher matcher = p.matcher(json);
				StringBuilder strBuilderField = new StringBuilder(1000);
				while(matcher.find()){
					strBuilderField.append(matcher.group());
				}	
				
				System.out.println("strbf: "+strBuilderField);
				
				// //d - possui numeros - faz com que undPAA:68, undPAA:860 vire so 68, 680
				//quando encontra so o padrao numeros ele joga no list codigos
				// joga somente os codigos dos paa (undPAA) para a lista
				// é so ver se o pab que o cara informou ta aq
				p = Pattern.compile("\\d+");
				matcher = p.matcher(strBuilderField.toString());
				while(matcher.find()){
					codigos.add(Long.valueOf(matcher.group()));
				}
				
				return codigos;
				
			  } catch (Exception e) 
				{
				  throw new Exception(e);
				}
					
		}
		
		
}
