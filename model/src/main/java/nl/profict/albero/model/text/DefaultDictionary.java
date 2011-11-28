package nl.profict.albero.model.text;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import nl.profict.albero.utilities.StringUtilities;

/**
 * A default {@link Dictionary dictionary} implementation.
 *
 * @author levi_h
 */
public class DefaultDictionary implements Dictionary {
	private Map<RoleAndKey, String> translations;

	/**
	 * Creates a default dictionary.
	 */
	public DefaultDictionary() {
		translations = new HashMap<RoleAndKey, String>();
	}

	/**
	 * Adds a translation to this dictionary. If a translation with the given role/key combination is already present,
	 * it will be replaced.
	 *
	 * @param role the role of the translation to add
	 * @param key the key of the translation to add
	 * @param translation the translation to add
	 */
	public void addTranslation(String role, String key, String translation) {
		translations.put(new RoleAndKey(role, key), translation);
	}

	public boolean hasTranslation(String role, String key) {
		return translations.containsKey(new RoleAndKey(role, key));
	}

	public String findTranslation(String role, String key) {
		return findTranslation(role, key, Collections.<String, Object>emptyMap());
	}

	public String findTranslation(String role, String key, Map<String, ?> parameters) {
		RoleAndKey roleAndKey = new RoleAndKey(role, key);

		String translation = translations.containsKey(roleAndKey) ? translations.get(roleAndKey) : key;

		return StringUtilities.interpolate(translation, parameters);
	}

	@Override
	public String toString() {
		return String.format("default dictionary (translations: %s)", translations);
	}

	private static class RoleAndKey {
		private String role;
		private String key;

		public RoleAndKey(String role, String key) {
			this.role = role;
			this.key = key;
		}

		@Override
		public int hashCode() {
			return role.hashCode() * 31 + key.hashCode() * 29 + 23;
		}

		@Override
		public boolean equals(Object object) {
			return (object instanceof RoleAndKey) && equals((RoleAndKey) object);
		}

		private boolean equals(RoleAndKey roleAndKey) {
			return role.equals(roleAndKey.role) && key.equals(roleAndKey.key);
		}

		@Override
		public String toString() {
			return String.format("(%s, %s)", role, key);
		}
	}
}