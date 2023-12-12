import eu.esdihumboldt.hale.common.lookup.*;
import eu.esdihumboldt.hale.ui.HaleUI;
import eu.esdihumboldt.hale.common.core.io.*;

def getLookupTableValue(value, tableId){
	// retrieve Lookup table
	LookupService ls = HaleUI.getServiceProvider().getService(LookupService.class)
	LookupTableInfo lithTable = ls.getTable(tableId)
	def table = lithTable.getTable()

	// get value from Lookup table
	def result = table.lookup(Value.of(value)).toString()
	return result
}

def collectAttributeZweckbestimmung(){
	if (_source.p.zweckbestimmung.value()) {
		collectAttributeAndFeatureTypeNameById('zweckbestimmung', _source.p.id.value())
	}
}

def collectAttributeDetailZweckbestimmung(){
	if (_source.p.detaillierteZweckbestimmung.value()) {
		collectAttributeAndFeatureTypeNameById('detaillierteZweckbestimmung', _source.p.id.value())
	}
}

def collectAttributeSondernutzung(){
	if (_source.p.sonderNutzung.value() || _source.p.sondernutzung.value()) {
		collectAttributeAndFeatureTypeNameById('sondernutzung', _source.p.id.value())
	}
}

def collectAttributeDetailSondernutzung(){
	if (_source.p.detaillierteSondernutzung.value()) {
		collectAttributeAndFeatureTypeNameById('detaillierteSondernutzung', _source.p.id.value())
	}
}

def collectAttributeAndFeatureTypeNameById(attributeName, id){
	withTransformationContext{
		def c = _.context.collector(it)
		
		def sourceType = _sourceTypes[0].typeDefinition.getDisplayName()
		c.complexeAttribute.sourceAttributeName[id] << attributeName
		c.complexeAttribute.sourceType[id] << sourceType
	}
}