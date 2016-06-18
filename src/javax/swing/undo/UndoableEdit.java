/*
 * Copyright (c) 1999, 2007, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package javax.swing.undo;

/**
 * An <code>UndoableEdit</code> represents an edit.  The edit may
 * be undone, or if already undone the edit may be redone.
 * <p>
 * <code>UndoableEdit</code> is designed to be used with the
 * <code>UndoManager</code>.  As <code>UndoableEdit</code>s are generated
 * by an <code>UndoableEditListener</code> they are typically added to
 * the <code>UndoManager</code>.  When an <code>UndoableEdit</code>
 * is added to an <code>UndoManager</code> the following occurs (assuming
 * <code>end</code> has not been called on the <code>UndoManager</code>):
 * <ol>
 * <li>If the <code>UndoManager</code> contains edits it will call
 *     <code>addEdit</code> on the current edit passing in the new edit
 *     as the argument.  If <code>addEdit</code> returns true the
 *     new edit is assumed to have been incorporated into the current edit and
 *     the new edit will not be added to the list of current edits.
 *     Edits can use <code>addEdit</code> as a way for smaller edits to
 *     be incorporated into a larger edit and treated as a single edit.
 * <li>If <code>addEdit</code> returns false <code>replaceEdit</code>
 *     is called on the new edit with the current edit passed in as the
 *     argument. This is the inverse of <code>addEdit</code> &#151;
 *     if the new edit returns true from <code>replaceEdit</code>, the new
 *     edit replaces the current edit.
 * </ol>
 * The <code>UndoManager</code> makes use of
 * <code>isSignificant</code> to determine how many edits should be
 * undone or redone.  The <code>UndoManager</code> will undo or redo
 * all insignificant edits (<code>isSignificant</code> returns false)
 * between the current edit and the last or
 * next significant edit.   <code>addEdit</code> and
 * <code>replaceEdit</code> can be used to treat multiple edits as
 * a single edit, returning false from <code>isSignificant</code>
 * allows for treating can be used to
 * have many smaller edits undone or redone at once.  Similar functionality
 * can also be done using the <code>addEdit</code> method.
 *
 * @author Ray Ryan
 */
public interface UndoableEdit {
    public void undo() throws CannotUndoException;

    public boolean canUndo();

    public void redo() throws CannotRedoException;

    public boolean canRedo();

    public void die();

    public boolean addEdit(UndoableEdit anEdit);

    public boolean replaceEdit(UndoableEdit anEdit);

    public boolean isSignificant();

    public String getPresentationName();

    public String getUndoPresentationName();

    public String getRedoPresentationName();
}
