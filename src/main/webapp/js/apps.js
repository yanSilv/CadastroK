angular.module("cadUsuario", []);
angular.module("cadUsuario").controller("cadUsuarioCtl", function ($scope, $window){
    $scope.login = [{nome: "", senha:""}];
    $scope.cad = [{nome: "", idade:"", endereco:"", telefone:""}];
    
    $scope.logarUser = function(login) {
        console.log(login.nome);
        $window.location.href = 'exibicao.html';
    };
    
    $scope.enviadados = function(cad) {
        console.log("Chegou aqui;;;;");
        console.log(cad.nome);
        
    };
});


