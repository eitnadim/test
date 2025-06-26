package com.eit.gateway.device.teltonika;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author Ernestas Vaiciukevicius (ernestas.vaiciukevicius@teltonika.lt)
 *
 *         <p>
 *         Avl data parser main class.
 *         </p>
 */
public class AvlDataParser {

	/**
	 * @param args
	 * @throws CodecException
	 * @throws IOException
	 * @throws IOException
	 
	public static void main(String[] args) throws CodecException, IOException {
		String hexData = null;
		if (args.length == 0) {

			System.exit(1);
		} else {
			if ("-".equals(args[0])) {
				BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
				hexData = reader.readLine();
			} else {
				hexData = args[0];
			}
		}

		// register supported codecs
		CodecStore.getInstance().register(AvlData.getCodec());
		CodecStore.getInstance().register(AvlDataFM4.getCodec());
		CodecStore.getInstance().register(AvlDataGH.getCodec());

		byte[] rawData = Tools.hexToBuffer(hexData);

		AvlData codec = CodecStore.getInstance().getSuitableCodec(rawData);

		if (codec == null) {

			System.exit(2);
		}

		AvlData[] avlData = codec.decode(rawData);

		for (AvlData item : avlData) {

		}
	} */
}