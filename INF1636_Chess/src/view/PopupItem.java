package view;

public enum PopupItem {
	SaveState("Salvar estado da partida"),
	Queen("Rainha"),
	Rook("Torre"),
	Bishop("Bispo"),
	Knight("Cavalo");
	
	private final String rawValue;
	
	private PopupItem(String rawValue) {
		this.rawValue = rawValue;
    }
	
	public String getRawValue() {
		return rawValue;
	}
	
	PopupItem getByRawValue(String rawValue) {
		switch (rawValue) {
		case "Salvar estado da partida":
			return SaveState;
		case "Rainha":
			return Queen;
		case "Torre":
			return Rook;
		case "Bispo":
			return Bishop;
		case "Cavalo":
			return Knight;
		default:
			return null;
		}
	}
}
