import http from 'k6/http';
import { sleep } from 'k6';

// Configurações do teste
export let options = {
    vus: 100,
    duration: '30s',
};

// Função para gerar um CPF aleatório (11 dígitos)
function randomCPF() {
    let cpf = '';
    for (let i = 0; i < 11; i++) {
        cpf += Math.floor(Math.random() * 10).toString();
    }
    return cpf;
}

export default function() {
    const cpf = randomCPF();
    http.post('http://localhost:8080/voto', JSON.stringify({
        "cpf": cpf,
        "idPauta": "1",
        "voto": "SIM"
    }), { headers: { 'Content-Type': 'application/json' } });
    sleep(1);
}
