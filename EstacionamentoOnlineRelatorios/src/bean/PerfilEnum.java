package bean;

public enum PerfilEnum {

	ADMINISTRADOR_ESTACIONAMENTO(2), 
	FUNCIONARIO(3), 
	CLIENTE(4);
	
	PerfilEnum(int codigoOpcao){
		codigo = codigoOpcao;
	}

	private final int codigo;

	public int getCodigo(){
		return codigo;
	}
	
}