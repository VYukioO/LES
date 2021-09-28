const CEP_BASE_URL = "https://viacep.com.br/ws"

// modais
const $address_modal        = $("#address_modal")
const $cart_modal           = $("#cart_modal")

// address modal fields
const $address_cep          = $("#address_cep")
const $address_state        = $("#address_state")
const $address_city         = $("#address_city")
const $address_neighbor     = $("#address_neighbor")
const $address_street       = $("#address_street")
const $address_number       = $("#address_number")
const $address_complement   = $("#address_complement")
const $address_nickname     = $("#address_nickname")
const $address_favorite     = $("#address_favorite")
const $address_type         = $("#address_type")

// cart modal fields
const $cart_number          = $("#cart_number")
const $cart_flag            = $("#cart_flag")
const $cart_date            = $("#cart_date")
const $cart_cvv             = $("#cart_cvv")
const $cart_name            = $("#cart_name")
const $cart_nickname        = $("#cart_nickname")
const $cart_favorite        = $("#cart_favorite")

// modal buttons
const $cart_add             = $("#cart_add")
const $address_add          = $("#address_add")

const qtdd_cart_inserted      = { index: 0 }
const qtdd_address_inserted      = { index: 0 }

$(document).ready(function () {
    $address_cep.mask("00000-000")
    $address_cep.blur(function(){
        findAddressData($(this))
    })
    $cart_number.blur(function(){
        if($(this).val().length >= 6){
            $($cart_flag).val(detectCardType($(this).val().replaceAll(" ", "").replaceAll("-", ""))) 
        } else {
            $($cart_flag).val("")
        }
    })
});


$cart_add.click(function(){
    if(!validateModalFields($cart_modal, true, elementsToNotValidate = ["cart_nickname", "cart_favorite"]))
        return false

    const element = $("#accordion_card").clone()
    configureAccordion(element, "cart_accordion_content_", qtdd_cart_inserted, "#cart_accordion")

    $(element).find('a').text($cart_nickname.val() === "" ? `Cartão de crédito ${qtdd_cart_inserted.index}` : $cart_nickname.val())

    $(element).find('.card-body').append(`
    <ul class="list-unstyled">
        <li><strong>Número: </strong>${$cart_number.val() || '-' }</li>
        <li><strong>Bandeira: </strong>${$cart_flag.val() || '-'}</li>
        <li><strong>CVV: </strong>${$cart_cvv.val() || '-'}</li>
        <li><strong>Validade até: </strong>${$cart_date.val() || '-'}</li>
        <li><strong>Nome completo: </strong>${$cart_name.val() || '-'}</li>
        <li><strong>Apelido: </strong>${$cart_nickname.val() || '-'}</li>
        <li><strong>É favorito: </strong>${$cart_favorite.is(":checked") ? "Sim" : "Não"}</li>
    
        <input type="hidden" name="cart_numero[${qtdd_cart_inserted.index-1}]" value="${$cart_number.val()}" />
        <input type="hidden" name="cart_bandeira[${qtdd_cart_inserted.index-1}]" value="${$cart_flag.val()}" />
        <input type="hidden" name="cart_cvv[${qtdd_cart_inserted.index-1}]" value="${$cart_cvv.val()}" />
        <input type="hidden" name="cart_validade[${qtdd_cart_inserted.index-1}]" value="${$cart_date.val()}" />
        <input type="hidden" name="cart_nome[${qtdd_cart_inserted.index-1}]" value="${$cart_name.val()}" />
        <input type="hidden" name="cart_apelido[${qtdd_cart_inserted.index-1}]" value="${$cart_nickname.val()}" />
        <input type="hidden" name="cart_favorito[${qtdd_cart_inserted.index-1}]" value="${$cart_favorite.is(":checked")}" />
     </ul>
    `)

    $('#cart_accordion').append(element)
    $cart_modal.modal("hide")

})

$address_add.click(function(){
    if(!validateModalFields($address_modal, true, elementsToNotValidate = ["address_nickname", "address_complement", "address_favorite"]))
        return false

    const element = $("#accordion_card").clone()
    configureAccordion(element, "address_accordion_content_", qtdd_address_inserted, "#address_accordion")

    $(element).find('a').text($address_nickname.val() === "" ? `Endereço ${qtdd_address_inserted.index}` : $cart_nickname.val())

    $(element).find('.card-body').append(`
    <ul class="list-unstyled">
        <li><strong>CEP: </strong>${$address_cep.val() || '-' }</li>
        <li><strong>Estado: </strong>${$address_state.val() || '-'}</li>
        <li><strong>Cidade: </strong>${$address_city.val() || '-'}</li>
        <li><strong>Bairro: </strong>${$address_neighbor.val() || '-'}</li>
        <li><strong>Rua: </strong>${$address_street.val() || '-'}</li>
        <li><strong>Número: </strong>${$address_number.val() || '-'}</li>
        <li><strong>Complemento: </strong>${$address_complement.val() || '-'}</li>
        <li><strong>Apelido: </strong>${$address_nickname.val() || '-'}</li>
        <li><strong>Favorito: </strong>${$address_favorite.is(":checked") ? "Sim" : "Não"}</li>
    
        <input type="hidden" name="address_cep[${qtdd_address_inserted.index-1}]" value="${$address_cep.val()}" />
        <input type="hidden" name="address_estado[${qtdd_address_inserted.index-1}]" value="${$address_state.val()}" />
        <input type="hidden" name="address_cidade[${qtdd_address_inserted.index-1}]" value="${$address_city.val()}" />
        <input type="hidden" name="address_bairro[${qtdd_address_inserted.index-1}]" value="${$address_neighbor.val()}" />
        <input type="hidden" name="address_rua[${qtdd_address_inserted.index-1}]" value="${$address_street.val()}" />
        <input type="hidden" name="address_numero[${qtdd_address_inserted.index-1}]" value="${$address_number.val()}" />
        <input type="hidden" name="address_complemento[${qtdd_address_inserted.index-1}]" value="${$address_complement.val()}" />
        <input type="hidden" name="address_apelido[${qtdd_address_inserted.index-1}]" value="${$address_nickname.val()}" />
        <input type="hidden" name="address_favorito[${qtdd_address_inserted.index-1}]" value="${$address_favorite.is(":checked")}" />
     </ul>
    `)

    $('#address_accordion').append(element)
    $address_modal.modal("hide")

})

$address_modal.on('hidden.bs.modal', function (e) {
    $(this)
      .find("input,textarea,select")
         .val('')
         .end()
      .find("input[type=checkbox], input[type=radio]")
         .prop("checked", "")
         .end();
})

$cart_modal.on('hidden.bs.modal', function (e) {
    $(this)
      .find("input,textarea,select")
         .val('')
         .end()
      .find("input[type=checkbox], input[type=radio]")
         .prop("checked", "")
         .end();
})

function findAddressData(field)  {
    const cep = field.val().replace("-","")

    if(cep.length !== 8)
        return false

    $.ajax({
        type: "GET",
        url: `${CEP_BASE_URL}/${cep}/json`,
    }).done((data) => {
        if(typeof data.erro !== "undefined")
            console.log("error")
        
        $address_state.val(data.uf)
        $address_city.val(data.localidade)
        $address_neighbor.val(data.bairro)
        $address_street.val(data.logradouro)
    }).fail((jqXHR, status, msg) => {
        console.log(status)
    });
}

function detectCardType(number) {
    let re = {
        electron: /^(4026|417500|4405|4508|4844|4913|4917)\d+$/,
        maestro: /^(5018|5020|5038|5612|5893|6304|6759|6761|6762|6763|0604|6390)\d+$/,
        dankort: /^(5019)\d+$/,
        interpayment: /^(636)\d+$/,
        unionpay: /^(62|88)\d+$/,
        visa: /^4[0-9]{12}(?:[0-9]{3})?$/,
        mastercard: /^5[1-5][0-9]{14}$/,
        amex: /^3[47][0-9]{13}$/,
        diners: /^3(?:0[0-5]|[68][0-9])[0-9]{11}$/,
        discover: /^6(?:011|5[0-9]{2})[0-9]{12}$/,
        jcb: /^(?:2131|1800|35\d{3})\d{11}$/
    }

    for(let key in re) {
        if(re[key].test(number)) {
            return key
        }
    }
}

function validateModalFields(modalElement, shouldValidate = true, elementsToNotValidate = []) {

    if(!shouldValidate)
        return true
    // pega todos os inputs e selects
    let elements = [
        ...modalElement.find('input'),
        ...modalElement.find('select')
    ]

    // verifica os campos que estão errados
    const toValidate = elements.filter((element, index) => {
        return ((element.value === "") || (element.type === 'checkbox' && !element.checked)) && !elementsToNotValidate.includes(element.id)
    }, elements)

    // mostra msg abaixo de cada field
    toValidate.map((element, index) => {
        $(element).parent().find('p').remove()
        $(element).parent().append(`<p class="form-text text-danger"><small>Campo <b>${element.title || "acima"}</b> não pode ser vazio</small></p>`)
    })

    // remove as msg quando o campo está validado
    elements.filter(function(x) {
        if(toValidate.indexOf(x) == -1) return true
    }).map(function(x){
        $(x).parent().find('p').remove()
    })

    if(toValidate.length > 0)
        return false
    else
        return true
}

function configureAccordion(element, name, counter, elementParent) {
    $(element).prop("id", "")
    $(element).find('.collapse').attr("id", `${name + counter.index}`)
    $(element).find('a').attr("href", `#${name + counter.index}`)
    $(element).find('a').attr("aria-controls", `${name + counter.index}`)
    $(element).find('a').attr("data-parent", `${elementParent}`)
    $(element).removeClass("d-none")
    counter.index++
}
