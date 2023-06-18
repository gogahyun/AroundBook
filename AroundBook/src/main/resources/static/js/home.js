$(document).ready(function() {
   $.ajax({
       url: "/api/itemImage",
       type: "get",
       contentType: "application/json; charset=utf-8;",
       dataType:"json",
       success: function(data) {
        var response = '';
        for (var i = 0; i < data.length; i++) {

            response += '<div class="col-md-3"><a href="/items/detail/'+data[i].id+'"><div class="card">'+
                            '<img class="card-img-top" height="380px" src="/img/testImg/' + data[i].image +
                            '"><div class="card-body"> <h5 class="card-title">' + data[i].title +
                            '</h5><pclass="card-text">' + data[i].price +
                            '</p></div></div></a></div>';
        }
        $('#homepageItems').append(response);

        return response
       }
   });
});
$('#testBtn').click(function(e){
    e.preventDefault();
    $('#testModal').modal("show");
});

$('#addr1 li > a').on('click', function() {
    // 버튼에 선택된 항목 텍스트 넣기
    $('#dropdownMenuButton').text($(this).text());
    $('#loc_addr1').val($(this).text());

    selectbox($(this).text());     //시/군/구 select 박스 호출
});

//select 박스에 도/특별시/광역시 에 맞는 시/군/구 입력
function selectbox(addr1) {
    let addr2 = document.querySelector("#addr2");
    var count=addr2.length;

    var Address2=[];
    switch(addr1) {
      case "서울":
       Address2 = ["강북구",	"도봉구",	"노원구", "중랑구",	"동대문구",	"성북구", "종로구",	"은평구",	"서대문구",	"마포구", "용산구",	"중구",	"성동구",	"광진구", 	"강동구",	"송파구", "강남구",	"서초구",	"동작구", "영등포구",	"강서구",	"양천구", 	"구로구",	"금천구",	"관악구"];
       break;
       case "경기":
       Address2 = ["김포시",	"고양시",	"파주시", "연천군",	"포천시",	"동두천시"	,"양주시",	"의정부시",	"구리시", "남양주시",	"가평군",	"양평군",	"여주시",	"광주시", "하남시",	"성남시",	"과천시",	"안양시", 	"광명시",	"부천시",	"시흥시",	"안산시",	"군포시", "의왕시",	"수원시",	"용인시", "이천시",	"안성시", "평택시",	"오산시",	"화성시"];break;
       case "인천":
       Address2 = ["계양구",	"부평구",	"남동구",	"연수구",	"미추홀구",	"중구",	"동구",	"서구",	"강화군",	"옹진군"];break;
       case "강원":
       Address2 = ["철원군",	"화천군",	"춘천시",	"양구군",	"인제군",	"고성군",	"속초시", "양양군"	,"홍천군",	"횡성군",	"평창군",	"강릉시",	"동해시",	"삼척시", "태백시",	"정선군",	"영월군",	"원주시"];break;
       case "충북":
       Address2 = ["단양군",	"제천시",	"충주시",	"음성군",	"진천군",	"증평군", "괴산군",	"청주시",	"보은군", "옥천군",	"영동군"];break;
       case "세종":
       Address2 = ["세종특별자치시"];break;
       case "충남":
       Address2 = ["천안시"	,"아산시"	,"당진시",	"서산시",	"태안군",	"홍성군",	"예산군",	"공주시",	"금산군",	"계룡시",	"논산시", "부여군",	"청양군",	"보령시",	"서천군"];break;
       case "대전":
       Address2 = ["유성구",	"대덕구"	,"동구",	"중구", "서구"];break;
       case "경북":
       Address2 = ["영주시",	"봉화군",	"울진군",	"영덕군",	"영양군",	"안동시",	"예천군",	"문경시", "상주시",	"의성군",	"청송군",	"포항시", "경주시",	"청도군",	"경산시",	"영천시","군위군",	"구미시",	"김천시",	"칠곡군", "성주군",	"고령군",	"울릉군"];break;
       case "대구":
       Address2 = ["동구",	"북구",	"서구"	,"중구", "수성구",	"남구",	"달서구",	"달성군" ];break;
       case "울산":
       Address2 = ["동구",	"북구",	"중구",	"남구",	"울주군"];break;
       case "부산":
       Address2 = ["기장군",	"금정구",	"북구",	"강서구",	"사상구", "부산진구",	"연제구"	,"동래구", "해운대구"	,"수영구",	"남구","동구",	"중구","영도구",	"서구",	"사하구"];break;
       case "경남":
       Address2 = ["함양군",	"거창군",	"합천군",	"창녕군",	"밀양시",	"양산시",	"김해시", "창원시", "함안군",	"의령군",	"산청군"	,"하동군",	"남해군",	"사천시",	"진주시",	"고성군","통영시",	"거제시"];break;
       case "전북":
       Address2 = ["군산시",	"김제시"	,"익산시",	"전주시", "완주군",	"진안군",	"무주군",	"장수군",	"남원시",	"임실군", "순창군",	"정읍시",	"부안군",	"고창군" ];break;
       case "전남":
       Address2 = ["영광군",	"함평군",	"장성군",	"담양군",	"곡성군",	"구례군",	"광양시",	"순천시", "화순군",	"나주시"	,"영암군"	,"무안군"	,"목포시"	,"신안군",	"진도군", "해남군",	"완도군",	"강진군",	"장흥군",	"보성군"	,"고흥군",	"여수시"];break;
       case "광주":
       Address2 =["북구",	"동구",	"남구",	"서구", "광산구"] ;break;
       v
       case "제주":
       Address2 = ["제주시",	"서귀포시"];break;
    }

    addr2.options.length = 0;
    Address2.forEach(function(j){
         var objOption = document.createElement("option");

        count = count+1;
        objOption.text = j;
        objOption.value = j;
        addr2.options.add(objOption);
    })
}