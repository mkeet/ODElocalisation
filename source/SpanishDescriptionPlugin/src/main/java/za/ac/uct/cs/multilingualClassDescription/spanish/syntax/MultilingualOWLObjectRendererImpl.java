package za.ac.uct.cs.multilingualClassDescription.spanish.syntax;

import org.protege.editor.owl.model.OWLModelManager;
import org.protege.editor.owl.ui.renderer.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.util.ShortFormProvider;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by Casey on 20-Mar-17.
 * Based on Matthew Horridge code - The University Of Manchester
 * Updated by Toky Raboanary - UCT University of Cape Town 13-Nov-2019
 */
public class MultilingualOWLObjectRendererImpl implements OWLObjectRenderer {

    private final OWLModelManager manager;
    private WriterDelegate writerDelegate;
    private MultilingualManchesterOWLSyntaxObjectRenderer renderer;

    public MultilingualOWLObjectRendererImpl(OWLModelManager manager) {
        this.manager = manager;
        writerDelegate = new WriterDelegate();

        //create an anonymous ShortFormProvider for use by the renderer
        ShortFormProvider provider = new ShortFormProvider(){
            public String getShortForm(OWLEntity owlEntity) {
                return MultilingualOWLObjectRendererImpl.this.manager.getRendering(owlEntity);
            }

            public void dispose() {
                // do nothing
            }
        };

        renderer = new MultilingualManchesterOWLSyntaxObjectRenderer(writerDelegate, provider);
    }

    @Override
    public String render(OWLObject owlObject) {
        if (owlObject instanceof OWLOntology)
            return null;
        writerDelegate.reset();
        owlObject.accept(renderer);
        return writerDelegate.toString();
    }

    private class WriterDelegate extends Writer {

        private StringWriter delegate;

        private void reset() {
            delegate = new StringWriter();
        }


        public String toString() {
            return delegate.getBuffer().toString();
        }


        public void close() throws IOException {
            delegate.close();
        }


        public void flush() throws IOException {
            delegate.flush();
        }


        public void write(char cbuf[], int off, int len) throws IOException {
            delegate.write(cbuf, off, len);
        }
    }
}
