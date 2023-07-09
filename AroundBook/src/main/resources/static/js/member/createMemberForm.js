//회원 등록
document.getElementById('createMemberSubmit').addEventListener('click',function (){
     var userId = document.getElementById("userId").value;
     var password = document.getElementById("password").value;
     var name = document.getElementById("name").value;
     var zipcode = document.getElementById("zipcode").value;
     var address = document.getElementById("address").value;

      fetch("/api/member", {
          method: "POST",
          headers: {"Content-Type":"application/json"},
          body: JSON.stringify({
              "userId":userId,
              "password":password,
              "name":name,
              "zipcode":zipcode,
              "address":address
          }),
      })
     .then(response => {
              if (response.ok) {
                  return response.json();
              }
              throw new Error('Something went wrong');
      })
      .then((responseJson) => {
          window.location.replace('/');
      })
      .catch((error) => {
          console.log(error)
      });
 })

//다음 API 이용 - 우편번호 시스템 + 주소
function findAddr() {
    new daum.Postcode({
        oncomplete: function(data) {
            var addr = ''; // 주소 변수
            var extraAddr = ''; // 참고항목 변수

            //사용자가 선택한 주소 타입에 따라 해당 주소 값을 가져온다.
            if (data.userSelectedType === 'R') { // 사용자가 도로명 주소를 선택했을 경우
                addr = data.roadAddress;
            } else { // 사용자가 지번 주소를 선택했을 경우(J)
                addr = data.jibunAddress;
            }

            document.getElementById('zipcode').value = data.zonecode;
            document.getElementById("address").value = addr;
        }
    }).open();
}