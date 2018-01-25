angular.module("cadUsuario", []);
angular.module("cadUsuario").controller("cadUsuarioCtl", function ($scope, $window){
    $scope.login = [{nome: "", senha:""}];
    
    $scope.logarUser = function(login) {
        console.log("Ola mundo");
        console.log(login.nome);
        console.log(login.senha);
        $window.location.href = 'exibicao.html';
    };
    
    $scope.cadastroUser=function (){
        
    };
});


