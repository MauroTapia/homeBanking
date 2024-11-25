# Home Banking

Aplicación creada con microservicios utilizando Spring Boot, Hibernate, Docker, Java y MySQL.

## Estructura del proyecto
![image](https://github.com/user-attachments/assets/cc314216-7c94-4fb9-9221-ce458fdf0778)


## Documentación

- [Testing y Kick Off](https://drive.google.com/drive/folders/1vVolvYuS63WcB6Fw6vY2M4vFJilzaaqt?usp=drive_link)
- [Peticiones en Postman](https://www.postman.com/red-trinity-613985/workspace/homebanking)

## Sprints

### Sprint I

**Historia de usuario:**  
*Como usuario, quiero registrarme en Home Banking para acceder y usar los servicios que ofrece.*

El servicio `user-service` gestiona el registro de usuarios dentro de nuestro IAM, permitiendo el inicio y cierre de sesión y aprovechando las funciones de Keycloak.

**Endpoint de registro:**  
`POST http://localhost:8084/user/register`

- No requiere autenticación.
- Datos necesarios: `name`, `lastName`, `username`, `email`, `phoneNumber`, `password`.
- Respuesta: Status 200 con el usuario creado (sin ID ni contraseña). Los campos `cvu` y `alias` se asignan aleatoriamente según los requisitos.

Los usuarios se registran en Keycloak y en nuestra base de datos, manteniendo la seguridad al no almacenar contraseñas directamente en la base de datos.

**Historias adicionales:**

- *Como usuario, quiero acceder a HomeBanking para realizar transferencias de fondos.*

Si las credenciales son correctas, Keycloak proporciona un token para utilizar los servicios.

### Sprint II

**Historia de usuario:**  
*Como usuario, necesito ver la cantidad de dinero disponible y los últimos 5 movimientos en mi billetera Home Banking.*

**Endpoint:**  
`GET http://localhost:8084/account/transactions`

- Requiere autenticación (Token).
- Respuesta: Últimas 5 transacciones del usuario.

**Historia de usuario:**  
*Como usuario, quiero ver mi perfil para consultar los datos de mi Cuenta Virtual Uniforme (CVU) y alias provistos por Home Banking.*

**Endpoint:**  
`GET http://localhost:8084/account/user-information`

- Requiere autenticación (Token).
- Respuesta: ID, balance de la cuenta, CVU y alias.

**Historia de usuario:**  
*Como usuario, me gustaría eliminar una tarjeta de débito o crédito cuando no quiera utilizarla más.*

**Endpoint:**  
`DELETE http://localhost:8084/account/delete-card/{cardNumber}`

- Requiere autenticación (Token).
- Respuesta: Confirmación de eliminación de la tarjeta.

### Sprint III - IV

**Historia de usuario:**  
*Como usuario, quiero ver toda la actividad realizada con mi billetera, desde la más reciente a la más antigua, para tener control de mis transacciones.*

**Endpoint:**  
`GET http://localhost:8084/account/activity`

- Requiere autenticación (Token).
- Respuesta: Toda la actividad de la cuenta.

**Historia de usuario:**  
*Como usuario, necesito el detalle de una actividad específica.*

**Endpoint:**  
`GET http://localhost:8084/account/activity/{transactionId}`

- Requiere autenticación (Token).
- Respuesta: Detalle de la actividad por ID de la transacción.

**Historia de usuario:**  
*Como usuario, me gustaría ingresar dinero desde mi tarjeta de crédito o débito a mi billetera Home Banking.*

**Endpoint:**  
`POST http://localhost:8084/account/deposit`

- Requiere autenticación (Token).
- Datos necesarios: Tarjeta para el depósito y monto.
- Respuesta: Confirmación del depósito.

**Historia de usuario:**  
*Como usuario, quiero poder enviar/transferir dinero a un CBU/CVU/alias desde mi saldo disponible en mi billetera.*

**Endpoint:**  
`POST http://localhost:8084/account/send-money`

- Requiere autenticación (Token).
- Datos necesarios: Alias destino y monto.
- Respuesta: Confirmación de la transferencia.
