angular.module("cadUsuario", []);

angular.value('urlBase', 'http://localhost:8080/sProduto/rest/');

angular.module("cadUsuario").controller("cadUsuarioCtl", function ($scope, $window, $http, urlBase){
    $scope.login = [{nome: "", senha:""}];
    $scope.cad = [{nome: "", idade:"", endereco:"", telefone:""}];
    
    $scope.logarUser = function(login) {
        console.log(login.nome);
        $http.post(urlBase + "acessologin/", {params: {
                usuario: login.nome,
                senha: login.senha
            }}).then(function (response) {
            console.log(response.data);

            /*if ($scope.totalItems === 0) {
                $scope.retornoConsulta = "Erro";
            } else {
                $scope.retornoConsulta = "Ok";
                $window.location.href = 'exibicao.html';
            }*/
        });
    };
    
    $scope.enviadados = function(cad) {
        console.log("Chegou aqui;;;;");
        console.log(cad.nome);
        
    };
});


