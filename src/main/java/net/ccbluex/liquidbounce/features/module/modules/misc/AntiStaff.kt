/*
 * FDPClient Hacked Client
 * A free open source mixin-based injection hacked client for Minecraft using Minecraft Forge by LiquidBounce.
 * https://github.com/SkidderMC/FDPClient/
 */
package net.ccbluex.liquidbounce.features.module.modules.misc

import net.ccbluex.liquidbounce.LiquidBounce
import net.ccbluex.liquidbounce.event.EventTarget
import net.ccbluex.liquidbounce.event.PacketEvent
import net.ccbluex.liquidbounce.event.WorldEvent
import net.ccbluex.liquidbounce.features.module.Module
import net.ccbluex.liquidbounce.features.module.ModuleCategory
import net.ccbluex.liquidbounce.features.module.ModuleInfo
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.Notification
import net.ccbluex.liquidbounce.ui.client.hud.element.elements.NotifyType
import net.ccbluex.liquidbounce.utils.ClientUtils
import net.ccbluex.liquidbounce.utils.misc.HttpUtils
import net.ccbluex.liquidbounce.value.BoolValue
import net.ccbluex.liquidbounce.value.ListValue
import net.ccbluex.liquidbounce.value.TextValue
import net.minecraft.network.play.server.S14PacketEntity
import net.minecraft.network.play.server.S1DPacketEntityEffect

@ModuleInfo(name = "AntiStaff", category = ModuleCategory.MISC)
class AntiStaff : Module() {

    private val serverValue = ListValue("Server", arrayOf("BlocksMC", "Jartex", "Pika", "Minebox", "Minemora", "Zonecraft", "Hycraft", "Librecraft","Custom"),"BlocksMC")
    private val notifyValue = BoolValue("Notification",true)
    private val chatValue = BoolValue("SendChatMessage",false)
    private val messageValue = TextValue("Message", "%staff% was detected as a staff member!").displayable { chatValue.get() }
    private val customURLValue = TextValue("CustomURL", "https://raw.githubusercontent.com/fdpweb/fdpweb.github.io/main/test").displayable { serverValue.equals("Custom") }

    private val leaveValue = BoolValue("Leave",true)
    private val leaveMessageValue = TextValue("LeaveCommand","/hub").displayable { leaveValue.get() }
    
    private var bmcStaff : String = " Jinaaan  _JustMix   Eissaa  1Mhmmd   mohmad_q8   1Brhom   Aliiyah   AssassinTime   ilyComsterr  PerfectRod_   xImTaiG_   Muntadher   xIBerryPlayz  ilyAhmmd   M4rwaan   iiRaivy   1LaB      1Sweet  Ev2n   1Daykel   Aboz3bl  iMehdi_   xMz7   EyesO_Diamond  qB6o6   506_   ZANAD   bu3qeel   Luvaa   devilsxul   ilySno   Sadlly  Thenvra   zayedk  MK_F16   FxKrown   TheDaddyJames   rcski   M7mmd   WiseActually   iLuvSG_   1BeSo   420kinaka   Refolt  iiM7mdZ_  iS3od_   1SenpaiM7md_   Flineer   1SaQeR   Fta7   KaaReeeM   _KG5   7y_   PT7  1RealFadi  CallMeWhatUWant  Driction  RealLoga   A3loosh   BoMshary  MrProfessor_T   1Opere   saleh2007   Sql7  Igisa   m7mdxjw   TheDrag_Xx  devilsqul   ilybb0   CsO__   KingHOYT   1Khalid   Firas   Mt3h   AFG_progamer92  ManiacR2H  7lawaah   SD_Safeh  Groshdom  i3li   PortGasDAces  AdvantageCheese  Solivion   iNolan_   1Ax_   0hBarry_  Aiiden   MX_7mode_yt  s2lm  ToFy_   Watchdog  S4KR  DetectiveFoxTY   Felings   Nikolas44   _H53   Malfore_   janet4  2xr1   zLeonides_   ilypush   d5qq  i9mm   Mondoros MondoEDRMondodaam79   _sadeq _sade  DeDeDeDerxayDeDeDeDerxa123   z7knn  90fa  SxAd_  zAsyx  IScaryum   SamyLoses SamyLoses a bebebebebNighbebebe1 AzAzAzAzIRA8  BlacK__KilleR  1Ivy_  stepmixer  YOUSEfTHEKING   vMuvMurd_ vMuvMurd_ INeerINee cricriall   puwsa   i_ABx    _ABrz   ABrz al _VaKris_ AnkaraView   StrengthSimp  UnKnownPrey  maybe_memaybe_memaybH2san   8eTaM   Moaazmohammed   1cutie   s2ad   2ali 2ali e h   xxmiin_   xmiin_  cccccce   Snjobh   JenJvL   ohm0r   2yod  1min_   BoM9er   Valiantess   KillMach1ne_  Jix4_1s 1Exhausted 1HeylmHasson_ 1L7NN 1LaB 1M7mdz 1M7mmD 1Maarc 1Mhmmd 1Nzar 1Pepe_ 1RealFadi Jinaaa  _JustMix Eissaa mohmad_q8 1Brhom Aliiyah AssassinTime Ahmmd PerfectRod_ xImTaiG_ xIBerryPlayz comsterr 1Sweet Ev2 WhereIsShe 1Daykel iMehdi_ Aboz3bl xMz7 EyesO_Diamond qB6o6 506_ ZANAD Luvaa Muntadher devilsxul Thenvra Sadlly zayedk iDrakola9 MK_F16 iiRaivy rcski M7mmd M4rwaa pretiosus iLuvSG_ 420kinaka iS3od_ Aymann_ Refolt 1SaQeR Maarcii Flineer Fta7 Y2me KaaReeeM Drictio PT7 DeeRx A3loosh ImPablo_ lqcs Mercilees MrProfessor_T JustDrink_ BoMshary w7r Sql7 Igisa S3rvox SlayerDarrk DangPavel LEGITT1st iRxV drqoo ilybb0 KingHOYT AFG_progamer92 Vesha throughthesky Solivio 7moooody_ e_Tv0i_DeMo Firas 0Aix Im_A Watchdog ToFy_ MX_7mode_yt G3rryx Felings Nikolas44 Malfore_ 2xr1 yosife_7Y BaSiL_123 _sadeq Destroyerxayu_ wl3d 90fa HerKinq_ Mondoros IScaryum Hardwaay dxvilsoul uncolour R4Z_DIGITAL 1Soin 1_3bdalH 1100 3AmOdi 16_Turtle_9 7lawaah 7s0 8mhh 69Kaatrin 2526 A7md_ Abdullahlq Aboal3z14 Agressives AhmedPROGG Alaam_FG Alfredo24151 AuthoritiesVenom Balz Ba6ee5man Banderr BinDontCare Boviix CaughtVaping Creegam Criv_IQ Crysling DelacroiiX DrMonteeey Driction Du7ym Dusity Ev2n FSJFESDFSE FakeButReal FexoraNEP ForMercyInsvun HM HackProtection HeartLost HerKing_ IMMT3H I_can_See ImXann Itchay IxKimo Jinaaan Jqlv Kh4nhMr Kuizano LX_D LipstickSmoker Lunching Lysreax M4rwaan MR1_ MaybeHeDoes top/minotar. MrProfessorT MythicalRain N6R Ne_TvOi_DeMoN OP1_ OnlyY2MaaC Player31 RIPPOR Raceth RealWayne Rebecca_jiaying Ruxq S217 SOYRUSSO SalemBayern_ ScarsFace Slave_Of_Sourish Solivion Spotzy. SpecialAdam_ StrangerM39ob StrengthSimp TheOnlyM7MAD TheRampage_king ToFy__ Tostiebramkaas TryLegitt Trylenn Turnks Veshan WherelsShe Y2men Y2serr Y_04 _ImKH _R3 _Vxpe _Seif _iSkyla _z2_ abd0_369 bota_69 bushiba d5qq feelupset fromthestart gwakf123 hsjwCZ i3li IA11 ¡AhmedGG ¡Dhoom iMajedi1 iS30d_ iSolom i_Ym5 ifeelgood91 ¡EsaTKing_ iiM7mdZ_ iikimo inVertice jaye kostasidk lovelywords manuelmaster mokgii 090x OMD_ obaida123445 opkp p89d puddinG_eee rwaeaeaeaea QUQU sunv sweetyheartt testtest50 tmtmo uh8e vFahad vMohamed vNext_ vdhvm vxom wtfprodm wzii xDupzHell xlBerryPlayz xlmAlone_ xlmM7mmd_ ximTaiG_ xLayrix yQuack yasuisforsale yff3 yzed zxcf_"
    private var jartexStaff : String = "voodootje0, Max, Rodagave, Wrath, JustThiemo, Andeh, Nirahz, stupxd, Yumpii, Botervrij, Viclyn_, ovq, NotSansy, DrogonMC, NotLoLo1818, ItzCqldFxld, Mykkull, Flappix, MaybeItsDestiny_, Knowly, SabitTSDM07, QB_poke, Opalestia, iFlyYT, Verwelkte, UnMonsieur, KT_798, AX79, Djim, DaddyZoiko, Serpentsalamce, Almostlikeaboss, ataman, Sweazi, ZoneRGH, naranbaatar, HeadsBreker, Difficulted, FuzniX, xHasey, ReddyPush, sammyxt".toString()
    private var pikaStaff : String = " Max  voodootje0  MrFrenco  JustThiemo Wrath  Andeh  Nirahz  stupxd  Yumpii  Botervrij   0Mad0Max0  Krekkers  Subvalent  Apo2xd  Arrly  Minecraft_leg   Hellific  CaptainGeoGR  Thijme01  Crni_    MrGownz   Outscale  MrEpiko  Crveni_Marlboro  zMqrcc  _Stella_xD   Alparo_  CandyOP  Astrospeh  StormTM577   ElementalMCR   SezarBD  ApocalypseFire2  Shivananda  "
    private var mineboxStaff : String = "xSp3ctro_  SaF3rC  Sagui  TheSuperXD_YT  xAnibal  xTheKillex25x  HankWalter  JavierFenix  inothayami  ChaosSoleil  ElChamo300  Robert TO1010  itachi_uchiha_s  roku__  rynne_ sushi dashi Vicky_21"
    private var zonecraftStaff : String = "002Aren Agu5 augusmaster BetTD d411 dunshbey85 ElMaGnific Pv ErCris Eugene FelmaxMC Gudaa ¡Enux ImMarvolo sleepless ismq ItzOmar16 joescam LuisPoMC Nicoxrm pacorro "
    private var hycraftStaff : String = "Alexander245 arqui Blandih Chony_15 jac0mc Ragen06 TheBryaan TMT_131 Yapecito MartynaGamer830 archeriam"
    private var librecraftStaff : String = "Kudos  H0DKIER  Iker_XD9  acreate  iJeanSC  acreate  Janet  Rosse_RM  aldoum23neko_  DERGO  MJKINGPAND"
    private var minemoraStaff : String = "Ruficraft MariSG iSebaas MaxyMC LuhGleh Esmorall SrLucchel_ ninjagod98 DarkFumado iDrecs CuencaDeDiamante PainSex"
    
    
    private var detected = false
    private var staffs = ""
    
    
    @EventTarget
    fun onWorld(event: WorldEvent) {
        when (serverValue.get().lowercase()) {
            "blocksmc" -> staffs = bmcStaff

            "jartex" -> staffs = jartexStaff

            "pika" -> staffs = pikaStaff

            "minebox" -> staffs = mineboxStaff

            "minemora" -> staffs = minemoraStaff
            
            "zonecraft" -> staffs = zonecraftStaff
            
            "hycraft" -> staffs = hycraftStaff
            
            "librecraft" -> staffs = librecraftStaff

            "custom" -> {
                try {
                    staffs = HttpUtils.get(customURLValue.get())

                    LiquidBounce.hud.addNotification(Notification("AntiStaff", "SuccessFully Loaded URL", NotifyType.SUCCESS, 1000))
                } catch (err: Throwable) {
                    LiquidBounce.hud.addNotification(Notification("AntiStaff", "Error when loading URL", NotifyType.ERROR, 1000))
                    println(err)
                }
            }
        }
            
        detected = false
    }

    @EventTarget
    fun onPacket(event: PacketEvent){
        if (mc.theWorld == null || mc.thePlayer == null) return

        val packet = event.packet // smart convert
        if (packet is S1DPacketEntityEffect) {
            val entity = mc.theWorld.getEntityByID(packet.entityId)
            if (entity != null && (staffs.contains(entity.name) || staffs.contains(entity.displayName.unformattedText))) {
                if (!detected) {
                    if (notifyValue.get()){
                        LiquidBounce.hud.addNotification(Notification(name, "Detected staff members with invis. You should quit ASAP.", NotifyType.WARNING, 8000))
                    }
                    
                    if (chatValue.get()) {
                        mc.thePlayer.sendChatMessage((messageValue.get()).replace("%staff%", entity.name))
                    }
                    if (leaveValue.get()) {
                        mc.thePlayer.sendChatMessage(leaveMessageValue.get())
                    }
                    
                    detected = true
                }
            }
        }
        if (packet is S14PacketEntity) {
            val entity = packet.getEntity(mc.theWorld)

            if (entity != null && (staffs.contains(entity.name) || staffs.contains(entity.displayName.unformattedText))) {
                if (!detected) {
                    if (notifyValue.get()){
                    LiquidBounce.hud.addNotification(Notification(name, "Detected staff members. You should quit ASAP.", NotifyType.WARNING,8000))
                    }
                    
                    if (chatValue.get()) {
                        ClientUtils.displayChatMessage((messageValue.get()).replace("%staff%", entity.name))
                    }
                    
                    if (leaveValue.get()) {
                        mc.thePlayer.sendChatMessage(leaveMessageValue.get())
                    }
                    
                    detected = true
                }
            }
        }
    }
}
