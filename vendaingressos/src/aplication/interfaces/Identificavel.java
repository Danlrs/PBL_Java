package aplication.interfaces;

import java.util.UUID;

/**
 * Interface que define um contrato para classes que devem possuir um identificador único.
 * Implementando esta interface, a classe se compromete a fornecer um metodo para obter
 * o identificador único (UUID).
 */
public interface Identificavel {

    /**
     * Retorna o identificador único da instância.
     *
     * @return UUID representando o identificador único da instância.
     */
    UUID getId();
}
