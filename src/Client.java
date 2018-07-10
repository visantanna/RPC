
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Random;

public class Client {
	
	static private String[] Messages = {"Iniciando operações de Retorno Void"
						,"Iniciando operações com long e 1 paramêtro de envio"
						,"Iniciando operações com long e 8 paramêtros de envio"
						,"Iniciando operações com string e 1 palavra"
						,"Iniciando operações com string e 2 palavras"
						,"Iniciando operações com string e 4 palavras"
						,"Iniciando operações com string e 8 palavras"
						,"Iniciando operações com string e 16 palavras"
						,"Iniciando operações com string e 32 palavras"
						,"Iniciando operações com string e 64 palavras"
						,"Iniciando operações com string e 128 palavras"
						,"Iniciando operações com string e 256 palavras"
						,"Iniciando operações com string e 512 palavras"
						,"Iniciando operações com string e 1024 palavras"}; 
	
	private Client() {}
	
	public static void main(String [] args) {
		String serverIP = (args.length < 1) ? "ec2-52-39-52-170.us-west-2.compute.amazonaws.com" : args[0];
		int serverPort = (args.length < 2) ? 1099 : Integer.parseInt(args[1]);
		try {
			RMIClient stub = new RMIClient(serverIP ,serverPort );
			System.out.println("Iniciando execução das operações");
			List<String> filesCreated = executeTests(stub);
			StringBuilder files = new StringBuilder();
			for(String file : filesCreated) {
				files.append(file + ", ");
			}
			System.out.println("Fim de execução resultados nos arquivos : " + files.substring(0,files.length() -2).toString());
			
		}catch (Exception e) {
			System.err.println("Client exception: " + e.toString());
			e.printStackTrace();
		}
	}

	private static List<String> executeTests(BasicRPCInterface stub) {
		Logger logger = new Logger();
		OperationAccounter operationAccounter = new OperationAccounter(stub);
		for(int nroOperacao = 1 ; nroOperacao < 15 ; nroOperacao ++) {
			Relatorio relatorio = new Relatorio(nroOperacao);
			System.out.println(Messages[nroOperacao-1]);
			for(int nroExecucoes = 0 ;  nroExecucoes < 10 ; nroExecucoes ++) {
				long tempoExecucao = operationAccounter.operation(nroOperacao);
				relatorio.addExecucao(tempoExecucao);
			}
			relatorio.setMedia(MathHelper.media(relatorio.getExecucoes()));
			relatorio.setDesvioPadrao(MathHelper.desvioPadrao(relatorio.getExecucoes()));
			logger.addRelatorio(relatorio);
		}
		return logger.log();
	}

}
class OperationAccounter{
	String[] listaPalavras = {"jelly" ,"stun" ,"collection" ,"stamp" ,"endorse" ,"rescue" ,"motif" ,"bishop" ,"parachute" ,"vat" ,"throw" ,"contemporary" ,"latest" ,"organ" ,"beginning" ,"drill" ,"develop" ,"crack" ,"prejudice" ,"nervous" ,"way" ,"offer" ,"correspondence" ,"proud" ,"mold" ,"precision" ,"dead" ,"blind" ,"reverse" ,"earthwax" ,"grand" ,"ideology" ,"mind" ,"golf" ,"residence" ,"abnormal" ,"Sunday" ,"heir" ,"pest" ,"terrace" ,"directory" ,"seasonal" ,"academy" ,"common" ,"danger" ,"legislature" ,"maid" ,"shallow" ,"control" ,"button" ,"lease" ,"egg" ,"cunning" ,"relevance" ,"veil" ,"mouth" ,"Koran" ,"motorist" ,"power" ,"relate" ,"notion" ,"bench" ,"potential" ,"social" ,"reproduce" ,"mutation" ,"supply" ,"car" ,"child" ,"enthusiasm" ,"conversation" ,"intelligence" ,"estate" ,"economy" ,"bottom" ,"judicial" ,"cane" ,"theorist" ,"section" ,"harm" ,"program" ,"insert" 
			,"prove" ,"breathe" ,"bow" ,"litigation" ,"chalk" ,"wine" ,"requirement" ,"privilege" ,"advertise" ,"stunning" ,"quality" ,"encourage" ,"lean" ,"context" ,"pupil" ,"safe" ,"medal" ,"jaw" ,"engagement" ,"belief" ,"electronics" ,"qualified" ,"even" ,"deprivation" ,"dependence" ,"light" ,"pin" ,"heart"  ,"flower" ,"horizon" ,"banish" ,"write" ,"series" ,"pop" ,"reference" ,"tired" ,"executive" ,"science" ,"blue" ,"leader" ,"chimpanzee" ,"nap" ,"chauvinist" ,"coverage" ,"complex" ,"mother" ,"coalition" ,"glimpse" ,"species" ,"item" ,"classify" ,"birthday" ,"innocent" ,"voter" ,"avant-garde" ,"knock" ,"silver" ,"couple" ,"meet" ,"pillow" ,"sodium" ,"drug" ,"grace" ,"daughter" ,"meeting" ,"negotiation" ,"equinox" ,"harmful" ,"fall" ,"makeup" ,"union" ,"mosquito" ,"humanity" ,"hallway" ,"false" ,"alarm" ,"archive" ,"labour" ,"survey" ,"affair" ,"tail" ,"folk" ,"credit" ,
			"introduction" ,"criminal" ,"access" ,"front" ,"chain" ,"palm" ,"representative" ,"crusade" ,"familiar" ,"master" ,"opera" ,"correction" ,"gradual" ,"shout" ,"debt" ,"pardon" ,"queue" ,"pursuit" ,"drift" ,"few" ,"tread" ,"office" ,"injury" ,"disturbance" ,"dominant" ,"bare" ,"cup" ,"contain" ,"negligence" ,"estimate" ,"crackpot" ,"toll" ,"obscure" ,"think" ,"trivial" ,"cower" ,"joystick" ,"illustrate" ,"healthy" ,"behavior" ,"paradox" ,"club" ,"anticipation" ,"edition" ,"harbor" ,"weakness" ,"care" ,"sail" ,"pain" ,"variation" ,"public" ,"salon" ,"horse" ,"girl" ,"cousin" ,"dialect" ,"honest" ,"bike" ,"sum" ,"like" ,"obese" ,"bleed" ,"candidate" ,"oak" ,"film" ,"missile" ,"wreck" ,"modernize" ,"doll" ,"pure" ,"short" ,"dismissal" ,"refund" ,"snap" ,"sheet" ,"live" ,"describe" ,"runner" ,"roof" ,"white" ,"observation" ,"kidney" ,"bald" ,"prevalence" ,"scream" ,
			"plagiarize" ,"quarrel" ,"realize" ,"figure" ,"coup" ,"tie" ,"thaw" ,"sense" ,"bad" ,"agency" ,"movement" ,"association" ,"suburb" ,"funeral" ,"global" ,"lawyer" ,"realism" ,"decoration" ,"attitude" ,"important" ,"cold" ,"force" ,"generation" ,"divide" ,"writer" ,"duty" ,"curve" ,"inappropriate" ,"bush" ,"vertical" ,"helicopter" ,"result" ,"flush" ,"manager" ,"maze" ,"stomach" ,"despair" ,"insistence" ,"resident" ,"presence" ,"speech" ,"impulse" ,"close" ,"proper" ,"colorful" ,"orbit" ,"complication" ,"snack" ,"pigeon" ,"indirect" ,"ensure" ,"deport" ,"acid" ,"text" ,"session" ,"blade" ,"receipt" ,"information" ,"spend" ,"confrontation" ,"glow" ,"jump" ,"stage" ,"council" ,"reluctance" ,"refer" ,"import" ,"baby" ,"clock" ,"rotate" ,"uncle" ,"pace" ,"inch" ,"mutual" ,"arch" ,"ritual" ,"commission" ,"attachment" ,"concede" ,"research" ,"identification" ,"joint" ,
			"bucket" ,"bland" ,"undertake" ,"standard" ,"term" ,"inspector" ,"sympathetic" ,"thumb" ,"detective" ,"return" ,"pedestrian" ,"pneumonia" ,"shell" ,"word" ,"gossip" ,"track" ,"valley" ,"attention" ,"consensus" ,"faint" ,"appreciate" ,"east" ,"launch" ,"shrink" ,"regulation" ,"bulletin" ,"chip" ,"collect" ,"fun" ,"wagon" ,"affinity" ,"mixture" ,"road" ,"excavation" ,"problem" ,"back" ,"ear" ,"temperature" ,"performer" ,"seller" ,"fork" ,"carve" ,"disaster" ,"stretch" ,"consciousness" ,"teacher" ,"response" ,"city" ,"margin" ,"language" ,"salmon" ,"spy" ,"aware" ,"chase" ,"cupboard" ,"stool" ,"dictate" ,"decade" ,"extract" ,"linen" ,"berry" ,"sell" ,"neighborhood" ,"fling" ,"turn" ,"bother" ,"confession" ,"zone" ,"clerk" ,"flow" ,"stain" ,"heroin" ,"rainbow" ,"mirror" ,"analysis" ,"soar" ,"cast" ,"bank" ,"positive" ,"carbon" ,"gap" ,"gift" ,"good" ,"shaft" ,"raw" ,
			"unity" ,"climb" ,"jealous" ,"ecstasy" ,"retreat" ,"aviation" ,"curtain" ,"objective" ,"steam" ,"ceremony" ,"coffin" ,"layer" ,"election" ,"vegetable" ,"cassette" ,"ice cream" ,"lot" ,"comfortable" ,"patrol" ,"straw" ,"grave" ,"psychology" ,"freshman" ,"forge" ,"relation" ,"rider" ,"diameter" ,"change" ,"concert" ,"entertainment" ,"director" ,"cat" ,"cheque" ,"survivor" ,"green" ,"jest" ,"test" ,"tasty" ,"warning" ,"cooperation" ,"texture" ,"trust" ,"struggle" ,"relinquish" ,"deteriorate" ,"management" ,"gaffe" ,"category" ,"tension" ,"finger" ,"choke" ,"area" ,"say" ,"linear" ,"hold" ,"excavate" ,"method" ,"remedy" ,"faithful" ,"yard" ,"height" ,"depart" ,"bell" ,"contrast" ,"herb" ,"shame" ,"folklore" ,"bus" ,"planet" ,"assignment" ,"chorus" ,"land" ,"try" ,"chance" ,"content" ,"load" ,"reform" ,"pavement" ,"negative" ,"provide" ,"quiet" ,"perfect" ,"dramatic" ,
			"biscuit" ,"rebellion" ,"catch" ,"gregarious" ,"combination" ,"energy" ,"automatic" ,"bold" ,"basic" ,"study" ,"drop" ,"coin" ,"cucumber" ,"cell phone" ,"sleeve" ,"possession" ,"rack" ,"clique" ,"infect" ,"index" ,"explicit" ,"feed" ,"mosaic" ,"tick" ,"studio" ,"conflict" ,"perform" ,"fur" ,"dressing" ,"stimulation" ,"punch" ,"spectrum" ,"cover" ,"straight" ,"reveal" ,"telephone" ,"preoccupation" ,"occasion" ,"great" ,"agree" ,"admission" ,"pound" ,"release" ,"straighten" ,"fiction" ,"guitar" ,"radical" ,"face" ,"slave" ,"square" ,"experience" ,"swim" ,"voucher" ,"round" ,"national" ,"large" ,"bake" ,"feedback" ,"soft" ,"cycle" ,"sun" ,"respectable" ,"pocket" ,"notebook" ,"freeze" ,"contact" ,"ton" ,"tender" ,"curriculum" ,"crisis" ,"circulation" ,"merit" ,"favor" ,"argument" ,"insure" ,"flavor" ,"mutter" ,"feather" ,"complete" ,"pie" ,"fist" ,"decorative" ,"dine" ,
			"deputy" ,"gallery" ,"dish" ,"wait" ,"camp" ,"classroom" ,"interface" ,"bird" ,"swop" ,"grass" ,"surprise" ,"firm" ,"highlight" ,"belong" ,"relieve" ,"routine" ,"unanimous" ,"silk" ,"horoscope" ,"environment" ,"linger" ,"retirement" ,"lost" ,"die" ,"meaning" ,"infrastructure" ,"glance" ,"fashionable" ,"random" ,"laboratory" ,"ministry" ,"reduction" ,"vain" ,"refrigerator" ,"gem" ,"storage" ,"appetite" ,"far" ,"market" ,"tire" ,"leash" ,"incentive" ,"deliver" ,"elect" ,"clash" ,"multimedia" ,"extinct" ,"scheme" ,"abundant" ,"ignore" ,"strip" ,"reputation" ,"tax" ,"trouble" ,"mess" ,"exotic" ,"skip" ,"embark" ,"delicate" ,"legislation" ,"man" ,"user" ,"regard" ,"understanding" ,"triangle" ,"bread" ,"muscle" ,"society" ,"coffee" ,"thirsty" ,"grief" ,"dynamic" ,"chief" ,"bitch" ,"heal" ,"net" ,"era" ,"cattle" ,"lonely" ,"comfort" ,"rough" ,"staircase" ,"AIDS" ,"spit" ,
			"traffic" ,"rugby" ,"direction" ,"sequence" ,"sister" ,"smooth" ,"volunteer" ,"ivory" ,"run" ,"spine" ,"accurate" ,"stock" ,"monopoly" ,"roar" ,"feeling" ,"design" ,"chemistry" ,"sphere" ,"seat" ,"consumption" ,"proportion" ,"end" ,"page" ,"delete" ,"ranch" ,"grow" ,"challenge" ,"beef" ,"superintendent" ,"urge" ,"dismiss" ,"chin" ,"diamond" ,"consider" ,"battlefield" ,"loud" ,"tool" ,"arrow" ,"owe" ,"wrist" ,"reasonable" ,"cheat" ,"discuss" ,"implication" ,"emotion" ,"stir" ,"feminine" ,"flourish" ,"aspect" ,"forward" ,"crutch" ,"shoot" ,"affect" ,"forbid" ,"resign" ,"fear" ,"export" ,"variable" ,"groan" ,"sickness" ,"critic" ,"reconcile" ,"safari" ,"dimension" ,"scan" ,"arise" ,"project" ,"trick" ,"pawn" ,"atmosphere" ,"compose" ,"abuse" ,"seal" ,"use" ,"bait" ,"sow" ,"bulb" ,"hay" ,"approve" ,"sight" ,"employee" ,"crash" ,"hen" ,"satellite" ,"slump" ,"drag" ,"give" ,"background" 
			,"witness" ,"communist" ,"buffet" ,"mist" ,"illness" ,"spring" ,"pull" ,"quit" ,"bless" ,"expect" ,"volcano" ,"doubt" ,"reflection" ,"retailer" ,"museum" ,"tycoon" ,"marketing" ,"sheep" ,"night" ,"ladder" ,"domestic" ,"willpower" ,"gown" ,"oven" ,"accident" ,"cabin" ,"high" ,"allowance" ,"fill" ,"ballet" ,"nuclear" ,"jurisdiction" ,"picture" ,"autonomy" ,"owner" ,"quotation" ,"economic" ,"penetrate" ,"assertive" ,"pitch" ,"resource" ,"dark" ,"loot" ,"betray" ,"paper" ,"commemorate" ,"entry" ,"housing" ,"toast" ,"citizen" ,"hover" ,"immune" ,"charismatic" ,"shelf" ,"invasion" ,"loss" ,"commitment" ,"agile" ,"teenager" ,"ready" ,"hope" ,"prefer" ,"portion" ,"park" ,"rational" ,"century" ,"survival" ,"licence" ,"cool" ,"source" ,"human body" ,"sanctuary" ,"package" ,"transfer" ,"shelter" ,"record" ,"excitement" ,"investment" ,"jam" ,"researcher" ,"conviction" ,"anniversary" ,"casualty" 
			,"crop" ,"burial" ,"final" ,"food" ,"primary" ,"width" ,"outlook" ,"glue" ,"economics" ,"trunk" ,"kick" ,"flash" ,"safety" ,"fine" ,"sermon" ,"calf" ,"calculation" ,"presidency" ,"symptom" ,"terms" ,"distant" ,"basin" ,"payment" ,"string" ,"node" ,"embarrassment" ,"marine" ,"discriminate" ,"interactive" ,"transaction" ,"neighbour" ,"assessment" ,"enter" ,"size" ,"abortion" ,"wriggle" ,"foster" ,"compartment" ,"machinery" ,"main" ,"waiter" ,"assumption" ,"flour" ,"school" ,"charge" ,"circle" ,"loyalty" ,"favorable" ,"surround" ,"limited" ,"fuel" ,"midnight" ,"license" ,"reptile" ,"arrogant" ,"route" ,"fax" ,"invisible" ,"hard" ,"progressive" ,"have" ,"clue" ,"restoration" ,"snarl" ,"smoke" ,"value" ,"performance" ,"temptation" ,"exclude" ,"revise" ,"decrease" ,"particular" ,"pay" ,"nationalism" ,"rabbit" ,"game" ,"appeal" ,"stand" ,"stereotype" ,"settle" ,"lift" ,"variety" ,"captivate"
			,"delivery" ,"plain" ,"miner" ,"compromise" ,"private" ,"coincidence" ,"location" ,"TRUE" ,"promotion" ,"withdraw" ,"cutting" ,"authority" ,"redeem" ,"proof" ,"raise" ,"discrimination" ,"outfit" ,"fate" ,"weight" ,"award" ,"weigh" ,"rhythm" ,"jury" ,"constraint" ,"skilled" ,"feign" ,"dozen" ,"established" ,"right wing" ,"sniff" ,"apathy" ,"annual" ,"rubbish" ,"notorious" ,"bomber" ,"exception" ,"extort" ,"tape" ,"nun" ,"despise" ,"cooperative" ,"expectation" ,"neck" ,"secular" ,"voyage" ,"empirical" ,"building" ,"pilot" ,"gesture" ,"justify" ,"translate" ,"congress" ,"match" ,"exact" ,"extent" ,"adult" ,"retire" ,"weapon" ,"measure" ,"breeze" ,"agreement" ,"officer" ,"incredible" ,"copy" ,"generate" ,"earthflax" ,"sketch" ,"murder" ,"trouser" ,"crevice" ,"hear" ,"right" ,"wind" ,"morning" ,"beer" ,"comedy" ,"nest" ,"tap" ,"fee" ,"rhetoric" ,"average" ,"explode" ,"profile" ,"mail carrier" 
			,"warn" ,"disorder" ,"fortune" ,"occupy" ,"cabinet" ,"confront" ,"tolerant" ,"script" ,"suspect" ,"refuse" ,"recession" ,"conclusion" ,"reliance" ,"deny" ,"acceptable" ,"inquiry" ,"stroll" ,"shave" ,"learn"};
	
	
	BasicRPCInterface stub;
	protected OperationAccounter(BasicRPCInterface stub ) {
		this.stub = stub;
	}
		
	protected long operation(int operation) {
		Random rand = new Random();
		long start = 0 ;
		long end =0;
		try {
			switch(operation) {
				case 1:
					start = System.currentTimeMillis();
					stub.funcaoVoid();
					end = System.currentTimeMillis();
					break;
				case 2:
					Long valor = rand.nextLong();
					start = System.currentTimeMillis();
					long result1 = stub.funcaoLong(valor);
					end = System.currentTimeMillis();
					break;
				case 3:
					long[] listaValores = new long[8]; 
					for(int i = 0 ; i < 8 ; i ++) {
						listaValores[i] = rand.nextLong();
						
					}
					start = System.currentTimeMillis();
					long result2 = stub.soma8longs(listaValores);
					end = System.currentTimeMillis();
					break;
				case 4:
				case 5:
				case 6:
				case 7:
				case 8:
				case 9:
				case 10:
				case 11:
				case 12:
				case 13:
				case 14:
					start = funcaoString(operation - 4);
					end = System.currentTimeMillis();
					break;
					
			}
		}catch(RemoteException e) {
			e.printStackTrace();
		}
		return end - start;
	}

	private long funcaoString(int mult ) throws RemoteException {
		int numeroPalavras = (int) Math.pow(2, mult);
		StringBuilder texto = new StringBuilder(); 
		for(int i = 0 ; i < numeroPalavras ; i ++){
			texto.append(listaPalavras[i] + " ");
		}
		String textoString = texto.toString();
		long start = System.currentTimeMillis();
		String result = this.stub.funcaoString(textoString);
		return start;
	}
}
