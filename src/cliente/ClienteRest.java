package cliente;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

@ManagedBean
@ApplicationScoped
public class ClienteRest
{
	public void checaPab() 
	{
		Long pab = 608L;
		Boolean retorno=false;
		
		Cabral cabral = new Cabral();
		try
		{
			for (Long pabs : cabral.obterPostosAtendimentoPorCodigoAgencia("0") )
			{
				if (pab.equals(pabs))
				{
					retorno=true;
				}
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("BOOL: "+retorno);
	}

}
