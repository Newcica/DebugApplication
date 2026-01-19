package com.example.myfirstapplication.wechat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myfirstapplication.databinding.ActivityContactsBinding
import java.util.*

class ContactsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactsBinding
    private lateinit var adapter: ContactAdapter
    private val allContacts = mutableListOf<ContactItem>()
    private val filteredContacts = mutableListOf<ContactItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initRecyclerView()
        loadSampleData()
        setupSearch()
        setupIndexBar()
    }

    private fun initToolbar() {
        binding.toolbar.title = "通讯录"
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initRecyclerView() {
        adapter = ContactAdapter { contact ->
            Toast.makeText(this, "点击联系人: ${contact.name}", Toast.LENGTH_SHORT).show()
            // 这里可以跳转到聊天界面或其他操作
        }

        binding.rvContacts.apply {
            layoutManager = LinearLayoutManager(this@ContactsActivity)
            adapter = this@ContactsActivity.adapter
            setHasFixedSize(true)
        }
    }

    private fun loadSampleData() {
        // 创建模拟数据
        val names = arrayOf(
            "张三", "李四", "王五", "赵六", "钱七", "孙八", "周九", "吴十",
            "刘一", "陈二", "林三", "郑四", "王五", "冯六", "蒋七", "沈八",
            "韩九", "杨十", "朱一", "秦二", "尤三", "许四", "何五", "吕六",
            "施七", "张八", "孔九", "曹十", "严一", "华二", "金三", "魏四",
            "陶五", "姜六", "戚七", "谢八", "邹九", "喻十", "柏一", "水二",
            "窦三", "章四", "云五", "苏六", "潘七", "葛八", "奚九", "范十",
            "彭一", "郎二", "鲁三", "韦四", "昌五", "马六", "苗七", "凤八",
            "花九", "方十", "俞一", "任二", "袁三", "柳四", "酆五", "鲍六",
            "史七", "唐八", "费九", "廉十", "岑一", "薛二", "雷三", "贺四",
            "倪五", "汤六", "滕七", "殷八", "罗九", "毕十", "郝一", "邬二",
            "安三", "常四", "乐五", "于六", "时七", "傅八", "皮九", "卞十",
            "齐一", "康二", "伍三", "余四", "元五", "卜六", "顾七", "孟八",
            "平九", "黄十", "和一", "穆二", "萧三", "尹四", "姚五", "邵六",
            "湛七", "汪八", "祁九", "毛十", "禹一", "狄二", "米三", "贝四",
            "明一", "臧二", "计三", "伏四", "成五", "戴六", "谈七", "宋八",
            "茅九", "庞十", "熊一", "纪二", "舒三", "屈四", "项五", "祝六",
            "董七", "梁八", "杜九", "阮十", "蓝一", "闵二", "席三", "季四",
            "麻五", "强六", "贾七", "路八", "娄九", "危十", "江一", "童二",
            "颜三", "郭四", "梅五", "盛六", "林七", "刁八", "钟九", "徐十",
            "邱一", "骆二", "高三", "夏四", "蔡五", "田六", "樊七", "胡八",
            "凌九", "霍十", "虞一", "万二", "支三", "柯四", "昝五", "管六",
            "卢七", "莫八", "经九", "房十", "裘一", "缪二", "干三", "解四",
            "应五", "宗六", "丁七", "宣八", "贲九", "邓十", "郁一", "单二",
            "杭三", "洪四", "包五", "诸六", "左七", "石八", "崔九", "吉十",
            "钮一", "龚二", "程三", "嵇四", "邢五", "滑六", "裴七", "陆八",
            "荣九", "翁十", "荀一", "羊二", "於三", "惠四", "甄五", "曲六",
            "家七", "封八", "芮九", "羿十", "储一", "靳二", "汲三", "邴四",
            "糜五", "松六", "井七", "段八", "富九", "巫十", "乌一", "焦二",
            "巴三", "弓四", "牧五", "隗六", "山七", "谷八", "车九", "侯十",
            "宓一", "蓬二", "全三", "郗四", "班五", "仰六", "秋七", "仲八",
            "伊九", "宫十", "宁一", "仇二", "栾三", "暴四", "甘五", "钭六",
            "厉七", "戎八", "祖九", "武十", "符一", "刘二", "景三", "詹四",
            "束五", "龙六", "叶七", "幸八", "司九", "韶十", "郜一", "黎二",
            "蓟三", "薄四", "印五", "宿六", "白七", "怀八", "蒲九", "邰十",
            "从一", "鄂二", "索三", "咸四", "籍五", "赖六", "卓七", "蔺八",
            "屠九", "蒙十", "池一", "乔二", "阴三", "郁四", "胥五", "能六",
            "苍七", "双八", "闻九", "莘十", "党一", "翟二", "谭三", "贡四",
            "劳五", "逄六", "姬七", "申八", "扶九", "堵十", "冉一", "宰二",
            "郦三", "雍四", "郤五", "璩六", "桑七", "桂八", "濮九", "牛十",
            "寿一", "通二", "边三", "扈四", "燕五", "冀六", "郏七", "浦八",
            "尚九", "农十", "温一", "别二", "庄三", "晏四", "柴五", "瞿六",
            "阎七", "充八", "慕九", "连十", "茹一", "习二", "宦三", "艾四",
            "鱼五", "容六", "向七", "古八", "易九", "慎十", "戈一", "廖二",
            "庾三", "终四", "暨五", "居六", "衡七", "步八", "都九", "耿十",
            "满一", "弘二", "匡三", "国四", "文五", "寇六", "广七", "禄八",
            "阙九", "东十", "欧一", "殳二", "沃三", "利四", "蔚五", "越六",
            "夔七", "隆八", "师九", "巩十", "厍一", "聂二", "晁三", "勾四",
            "敖五", "融六", "冷七", "訾八", "辛九", "阚十", "那一", "简二",
            "饶三", "空四", "曾五", "毋六", "沙七", "乜八", "养九", "鞠十",
            "须一", "丰二", "巢三", "关四", "蒯五", "相六", "查七", "后八",
            "荆九", "红十", "游一", "竺二", "权三", "逯四", "盖五", "益六",
            "桓七", "公八", "万俟九", "司马十", "上官一", "欧阳二", "夏侯三", "诸葛四",
            "闻人五", "东方六", "赫连七", "皇甫八", "尉迟九", "公羊十", "澹台一", "公冶二",
            "宗政三", "濮阳四", "淳于五", "单于六", "太叔七", "申屠八", "公孙九", "仲孙十",
            "轩辕一", "令狐二", "钟离三", "宇文四", "长孙五", "慕容六", "鲜于七", "闾丘八",
            "司徒九", "司空十", "亓官一", "司寇二", "仉督三", "子车四", "颛孙五", "端木六",
            "巫马七", "公西八", "漆雕九", "乐正十", "壤驷一", "公良二", "拓跋三", "夹谷四",
            "宰父五", "谷梁六", "晋楚七", "闫法八", "汝鄢九", "涂钦十", "段干一", "百里二",
            "东郭三", "南门四", "呼延五", "归海六", "羊舌七", "微生八", "岳帅九", "缑亢十",
            "况后有琴一", "梁丘司二", "上官欧阳三", "夏侯诸葛四", "闻人东方五", "赫连皇甫六", "尉迟公羊七", "澹台公冶八",
            "宗政濮阳九", "淳于单于十", "太叔申屠一", "公孙仲孙二", "轩辕令狐三", "钟离宇文四", "长孙慕容五", "鲜于闾丘六",
            "司徒司空七", "亓官司寇八", "仉督子车九", "颛孙端木十", "巫马公西一", "漆雕乐正二", "壤驷公良三", "拓跋夹谷四",
            "宰父谷梁五", "晋楚闫法六", "汝鄢涂钦七", "段干百里八", "东郭南门九", "呼延归海十", "羊舌微生一", "岳帅缑亢二",
            "况后有琴三", "梁丘司上官四", "欧阳夏侯五", "诸葛闻人六", "东方赫连七", "皇甫尉迟八", "公羊澹台九", "公冶宗政十"
        )

        val contacts = mutableListOf<Contact>()
        for (name in names) {
            val firstLetter = getFirstLetter(name)
            contacts.add(
                Contact(
                    id = "${contacts.size}",
                    name = name,
                    firstLetter = firstLetter,
                    signature = "个性签名：$name 的签名"
                )
            )
        }

        // 按首字母排序
        val sortedContacts = contacts.sortedWith { c1, c2 ->
            c1.firstLetter.compareTo(c2.firstLetter)
        }

        // 按首字母分组
        allContacts.clear()
        var currentLetter = ""
        for (contact in sortedContacts) {
            if (contact.firstLetter != currentLetter) {
                currentLetter = contact.firstLetter
                allContacts.add(ContactItem.Header(currentLetter))
            }
            allContacts.add(ContactItem.ContactItemData(contact))
        }

        filteredContacts.clear()
        filteredContacts.addAll(allContacts)
        adapter.submitList(filteredContacts.toList())
    }

    private fun getFirstLetter(name: String): String {
        if (name.isEmpty()) return "#"

        val firstChar = name[0]
        return if (firstChar in 'A'..'Z' || firstChar in 'a'..'z') {
            firstChar.uppercase()
        } else {
            // 对中文字符获取拼音首字母，这里简化处理
            when (firstChar) {
                in '啊'..'傲' -> "A"
                in '八'..'灞' -> "B"
                in '嚓'..'错' -> "C"
                in '大'..'蛋' -> "D"
                in '额'..'扼' -> "E"
                in '发'..'繁' -> "F"
                in '噶'..'阁' -> "G"
                in '哈'..'合' -> "H"
                in '及'..'既' -> "J"
                in '喀'..'客' -> "K"
                in '拉'..'烙' -> "L"
                in '妈'..'嘛' -> "M"
                in '拿'..'纳' -> "N"
                in '哦'..'沤' -> "O"
                in '啪'..'批' -> "P"
                in '七'..'且' -> "Q"
                in '然'..'日' -> "R"
                in '撒'..'塞' -> "S"
                in '它'..'糖' -> "T"
                in '哇'..'万' -> "W"
                in '夕'..'下' -> "X"
                in '压'..'跃' -> "Y"
                in '匝'..'坐' -> "Z"
                else -> "#"
            }
        }
    }

    private fun setupSearch() {
        // 为搜索框添加点击事件，实际项目中可能需要替换为EditText
        binding.searchContainer.setOnClickListener {
            Toast.makeText(this, "点击了搜索框", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupIndexBar() {
        binding.indexBar.setOnIndexChangeListener { letter ->
            binding.tvLetterTip.text = letter
            binding.tvLetterTip.visibility = android.view.View.VISIBLE

            // 找到对应字母的第一个位置并滚动
            val position = findFirstPositionByLetter(letter)
            if (position != -1) {
                binding.rvContacts.scrollToPosition(position)
            }
        }

        // 隐藏字母提示
        binding.indexBar.setOnTouchListener { _, event ->
            when (event.action) {
                android.view.MotionEvent.ACTION_UP, android.view.MotionEvent.ACTION_CANCEL -> {
                    binding.tvLetterTip.visibility = android.view.View.GONE
                }
            }
            return@setOnTouchListener false
        }
    }

    private fun findFirstPositionByLetter(letter: String): Int {
        allContacts.forEachIndexed { index, contactItem ->
            if (contactItem is ContactItem.Header && contactItem.title == letter) {
                return index
            }
        }
        return -1
    }
}