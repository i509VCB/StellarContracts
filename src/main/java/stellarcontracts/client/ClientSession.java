package stellarcontracts.client;

public final class ClientSession {
	static ClientSession session;

	public static ClientSession getCurrentSession() throws IllegalStateException {
		if (session != null) {
			return session;
		}

		throw new IllegalStateException();
	}
}
