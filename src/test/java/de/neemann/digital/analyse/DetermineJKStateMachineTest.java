package de.neemann.digital.analyse;


import de.neemann.digital.analyse.expression.Expression;
import de.neemann.digital.analyse.expression.format.FormatToExpression;
import de.neemann.digital.analyse.expression.format.FormatterException;
import junit.framework.TestCase;

import static de.neemann.digital.analyse.expression.Not.not;
import static de.neemann.digital.analyse.expression.Operation.and;
import static de.neemann.digital.analyse.expression.Operation.or;
import static de.neemann.digital.analyse.expression.Variable.v;


/**
 * @author hneemann
 */
public class DetermineJKStateMachineTest extends TestCase {

    private Expression a;
    private Expression nota;
    private Expression b;
    private Expression notb;
    private Expression c;
    private Expression notc;

    public void setUp() throws Exception {
        a = v("a");
        nota = not(a);
        b = v("b");
        notb = not(b);
        c = v("c");
        notc = not(c);
    }

    public void testSimple() throws Exception {

        Expression e = or(and(a, c), and(nota, notb));

        DetermineJKStateMachine jk = new DetermineJKStateMachine("a", e);
        assertEquals(toStr(notb), toStr(jk.getJ()));
        assertEquals(toStr(notc), toStr(jk.getK()));
        assertFalse(jk.isDFF());
    }

    private String toStr(Expression expression) throws FormatterException {
        return FormatToExpression.FORMATTER_UNICODE.format(expression);
    }

    public void testSimple2() throws Exception {

        Expression e = or(and(a, c), and(nota, notb), and(b, c));

        DetermineJKStateMachine jk = new DetermineJKStateMachine("a", e);
        assertEquals("(b ∧ c) ∨ ¬b", toStr(jk.getJ()));
        assertEquals("¬((b ∧ c) ∨ c)", toStr(jk.getK()));
        assertFalse(jk.isDFF());
    }

    public void testSimple3() throws Exception {
        Expression e = or(nota);

        DetermineJKStateMachine jk = new DetermineJKStateMachine("a", e);
        assertEquals("1", toStr(jk.getJ()));
        assertEquals("1", toStr(jk.getK()));
        assertFalse(jk.isDFF());
    }

    public void testSimple4() throws Exception {
        Expression e = or(a);

        DetermineJKStateMachine jk = new DetermineJKStateMachine("a", e);
        assertEquals("0", toStr(jk.getJ()));
        assertEquals("0", toStr(jk.getK()));
        assertFalse(jk.isDFF());
    }

    public void testSimpleD() throws Exception {
        Expression e = or(and(a, b), and(nota, notb));

        DetermineJKStateMachine jk = new DetermineJKStateMachine("c", e);
        assertEquals("(¬a ∧ ¬b) ∨ (a ∧ b)", toStr(jk.getJ()));
        assertEquals("(¬a ∧ ¬b) ∨ (a ∧ b)", toStr(jk.getNK()));
        assertTrue(jk.isDFF());
    }

    public void testSimpleBUG() throws Exception {
        Expression e = or(b, nota);

        DetermineJKStateMachine jk = new DetermineJKStateMachine("a", e);
        assertEquals("1", toStr(jk.getSimplifiedJ()));
        assertEquals("¬b", toStr(jk.getSimplifiedK()));
        assertFalse(jk.isDFF());
    }

}