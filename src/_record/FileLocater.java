package _record;



public class FileLocater {
	public static FileLocater[] list;
	public static final int SCHOOL_NUM = 5;
	
	
	public final String name;
	public final String[] dept;
	public final int deptNum;
	
		
	
	static {
		
		list = new FileLocater[SCHOOL_NUM];
		

		
		String[] busi = new String[10];
		busi[0] = "ACCT";
		busi[1] = "BIEN";
		busi[2] = "BTEC";
		busi[3] = "CBME";
		busi[4] = "CENG";
		busi[5] = "CHEM";
		busi[6] = "CIEM";
		busi[7] = "CIVL";
		busi[8] = "COMP";
		busi[9] = "CSIT";
		
		
		list[0] = new FileLocater("A-C",busi, 10);
		
		String[] eng = new String[16];
		eng[0] = "ECON";
		eng[1] = "EEMT";
		eng[2] = "EESM";
		eng[3] = "ELEC";
		eng[4] = "ENEG";
		eng[5] = "ENGG";
		eng[6] = "ENTR";
		eng[7] = "ENVR";
		eng[8] = "ENVS";
		eng[9] = "EVNG";
		eng[10] = "EVSM";
		eng[11] = "FINA";
		eng[12] = "FYTG";
		eng[13] = "GBUS";
		eng[14] = "GFIN";
		eng[15] = "GNED";
		
		
		list[1] = new FileLocater("E-G", eng, 16);
		
		String[] scie = new String[11];
		scie[0] = "HART";
		scie[1] = "HLTH";
		//scie[2] = "HMMA";
		scie[2] = "HUMA";
		scie[3] = "IBTM";
		scie[4] = "IDPO";
		scie[5] = "IELM";
		scie[6] = "ISOM";
		scie[7] = "JEVE";
		scie[8] = "LABU";
		//scie[10] = "LAGR";
		scie[9] = "LANG";
		scie[10] = "LIFS";
	
		list[2] = new FileLocater("H-L", scie, 11);
		
		String[] huma = new String[11];
		huma[0] = "MAFS";
		//huma[1] = "MALS";
		huma[1] = "MARK";
		huma[2] = "MATH";
		huma[3] = "MECH";
		huma[4] = "MESF";
		huma[5] = "MGCS";
		huma[6] = "MGMT";
		huma[7] = "MIMT";
		huma[8] = "NANO";
		huma[9] = "PDEV";
		huma[10] = "PHYS";
		
		list[3] = new FileLocater("M-P", huma, 11);
		
		String[] lang = new String[8];
		lang[0] = "RMBI";
		lang[1] = "SBMT";
		lang[2] = "SCED";
		lang[3] = "SCIE";
		lang[4] = "SHSS";
		lang[5] = "SOSC";
		lang[6] = "SSMA";
		lang[7] = "TEMG";

		list[4] = new FileLocater("R-T", lang, 8);
				
	}

		
	FileLocater(String s, String[] depts, int num) {
			name = s;
			dept = depts;
			deptNum = num;

	}
	
	public String toString()  {
		return name;
	}
		

}
