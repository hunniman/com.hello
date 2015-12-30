package msgPackageTest;

import java.io.IOException;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;

public class ObjectTemplate <T> extends AbstractTemplate<T>{

	private Class<?> dclass=null;
	
	public ObjectTemplate (Class<T> dc){
		this.dclass=dc;
	}
	@Override
	public void write(Packer pk, T target, boolean required) throws IOException {
		if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.write((T) target);
	}

	@SuppressWarnings("unchecked")
	@Override
	public T read(Unpacker u, T to, boolean required) throws IOException {
		 if (!required && u.trySkipNil()) {
	            return null;
	        }
	        return (T) u.read(dclass);
	}

	

}
