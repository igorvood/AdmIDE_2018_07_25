package ru.vood.admplugin.dialogs.ExtSwing;

import ru.vood.admplugin.infrastructure.spring.entity.VBdObjectEntity;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

public abstract class JAddDialog extends JDialog {
    private VBdObjectEntity addedObj;

    /**
     * Creates a modeless dialog with the specified {@code Frame}
     * as its owner and an empty title. If {@code owner}
     * is {@code null}, a shared, hidden frame will be set as the
     * owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * @param owner the {@code Frame} from which the dialog is displayed
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *                           returns {@code true}.
     * @see GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JAddDialog(Frame owner) {
        super(owner);
    }

    /**
     * Creates a modeless dialog without a title and without a specified
     * {@code Frame} owner.  A shared, hidden frame will be
     * set as the owner of the dialog.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     * <p>
     * NOTE: This constructor does not allow you to create an unowned
     * {@code JDialog}. To create an unowned {@code JDialog}
     * you must use either the {@code JDialog(Window)} or
     * {@code JDialog(Dialog)} constructor with an argument of
     * {@code null}.
     *
     * @throws HeadlessException if {@code GraphicsEnvironment.isHeadless()}
     *                           returns {@code true}.
     * @see GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JAddDialog() {
        super();
    }

    /**
     * Creates a modeless dialog with the specified {@code Dialog}
     * as its owner and an empty title.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by {@code JComponent.getDefaultLocale}.
     *
     * @param owner the owner {@code Dialog} from which the dialog is displayed
     *              or {@code null} if this dialog has no owner
     * @throws HeadlessException {@code if GraphicsEnvironment.isHeadless()}
     *                           returns {@code true}.
     * @see GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    public JAddDialog(Dialog owner) {
        super(owner);
    }

    /**
     * Shows or hides this {@code Dialog} depending on the value of parameter
     * {@code b}.
     *
     * @param b if {@code true}, makes the {@code Dialog} visible,
     *          otherwise hides the {@code Dialog}.
     *          If the dialog and/or its owner
     *          are not yet displayable, both are made displayable.  The
     *          dialog will be validated prior to being made visible.
     *          If {@code false}, hides the {@code Dialog} and then causes {@code setVisible(true)}
     *          to return if it is currently blocked.
     *          <p>
     *          <b>Notes for modal dialogs</b>.
     *          <ul>
     *          <li>{@code setVisible(true)}:  If the dialog is not already
     *          visible, this call will not return until the dialog is
     *          hidden by calling {@code setVisible(false)} or
     *          {@code dispose}.
     *          <li>{@code setVisible(false)}:  Hides the dialog and then
     *          returns on {@code setVisible(true)} if it is currently blocked.
     *          <li>It is OK to call this method from the event dispatching
     *          thread because the toolkit ensures that other events are
     *          not blocked while this method is blocked.
     *          </ul>
     * @see Window#setVisible
     * @see Window#dispose
     * @see Component#isDisplayable
     * @see Component#validate
     * @see Dialog#isModal
     */
    @Override
    public void setVisible(boolean b) {
        extension();
        setCenterPosition();
        super.setVisible(b);
    }

    private void setCenterPosition() {
        int screenWidth;
        int screenHeight;
        GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice devices = environment.getDefaultScreenDevice();
        DisplayMode dmode = devices.getDisplayMode();
        screenWidth = dmode.getWidth();
        screenHeight = dmode.getHeight();
        BigDecimal h = new BigDecimal(screenHeight / 2 - this.getSize().getHeight() / 2);
        BigDecimal w = new BigDecimal(screenWidth / 2 - this.getSize().getWidth() / 2);
        this.setLocation(w.setScale(0, BigDecimal.ROUND_UP).intValue(),
                h.setScale(0, BigDecimal.ROUND_UP).intValue()); //Size(new Dimension(1212, 553));
    }

    /**
     * Дополнительные действия, запускается при вызове setVisible
     */
    protected abstract void extension();

    public VBdObjectEntity getAddedObj() {
        return addedObj;
    }

    protected void setAddedObj(VBdObjectEntity addedObj) {
        this.addedObj = addedObj;
    }

    protected boolean checkText(JTextField textField) {
        return textField != null && textField.getText() != null && textField.getText().length() > 0;
    }
}
