package com.exact.ews.exhaustive;

import com.exact.ews.exhaustive.BaseTestCase;
import com.exact.ews.Encoding;
import com.exact.ews.TestUtils;
import com.exact.ews.transaction.Request;
import com.exact.ews.transaction.Response;
import com.exact.ews.transaction.enums.TransactionType;

/**
 * User: donch
 * Date: 05-Aug-2009
 * Time: 09:36:35
 */
public class PreAuthCompletionTest extends BaseTestCase
{
  private String authorizationNum = null;

  public PreAuthCompletionTest(final String name) {
    super(name);
  }

  public void testMandatory() {
    final Request request = new Request(TransactionType.PreAuthCompletion);
    assertFalse(request.isValid());

    assertTrue(request.getErrors().contains("ExactId has not been set."));
    request.setExactId(TestUtils.EmergisExactID);
    assertFalse(request.isValid());

    assertTrue(request.getErrors().contains("Password has not been set."));
    request.setPassword(TestUtils.EmergisPassword);
    assertFalse(request.isValid());

    assertTrue(request.getErrors().contains("One of the following must be supplied: Card Number, Track1, Track2 or TransactionTag."));
    request.setCardNumber(TestUtils.CCNumber);
    assertFalse(request.isValid());

    assertTrue(request.getErrors().contains("Card Expiry Date is required."));
    request.setCardExpiryDate(TestUtils.CCExpiry);
    assertFalse(request.isValid());

    assertTrue(request.getErrors().contains("Cardholder Name is required."));
    request.setCardholderName(TestUtils.CardholderName);
    assertFalse(request.isValid());

    assertTrue(request.getErrors().contains("Amount is required."));
    request.setAmount(10.0f);
    assertFalse(request.isValid());

    assertTrue(request.getErrors().contains("Authorization Number is required."));
    request.setAuthorizationNum("ET7868");
    assertTrue(request.isValid());
  }

  public void testByCreditCard() {
    final Request request = getRequestByCCNumber(TransactionType.PreAuthCompletion);
    request.setAmount(3.0f);
    request.setAuthorizationNum(sendPreAuth());
    assertTrue(request.isValid());

    // JSON
    Response response = submit(request, Encoding.JSON);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);

    // REST
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first

    response = submit(request, Encoding.REST);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);

    // SOAP
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first

    response = submit(request, Encoding.SOAP);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);
  }

  public void testByTrack1() {
    final Request request = getRequestByTrack1(TransactionType.PreAuthCompletion);
    request.setAmount(10.0f);
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first
    assertTrue(request.isValid());

    // JSON
    Response response = submit(request, Encoding.JSON);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);

    // REST
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first

    response = submit(request, Encoding.REST);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);

    // SOAP
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first

    response = submit(request, Encoding.SOAP);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);
  }

  public void testByTrack2() {
    final Request request = getRequestByTrack2(TransactionType.PreAuthCompletion);
    request.setAmount(10.0f);
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first
    assertTrue(request.isValid());

    // JSON
    Response response = submit(request, Encoding.JSON);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);

    // REST
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first

    response = submit(request, Encoding.REST);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);

    // SOAP
    request.setAuthorizationNum(sendPreAuth()); // need to submit a new PreAuth first

    response = submit(request, Encoding.SOAP);
    assertTrue(response.isApproved());
    assertEquals(request.getAmount(), response.getRequest().getAmount());
    checkCreditCardDetails(response);
  }

  // send a PreAuth request, and return its authorizationNum so we can complete
  private String sendPreAuth()
  {
    final Request request = getRequestByCCNumber(TransactionType.PreAuth);
    request.setAmount(10.0f);

    final Response response = submit(request, Encoding.JSON);
    assertTrue(response.isApproved());

    return response.getRequest().getAuthorizationNum();
  }

}
