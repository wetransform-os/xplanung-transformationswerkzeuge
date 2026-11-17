import eu.esdihumboldt.hale.common.lookup.*;
import eu.esdihumboldt.hale.common.core.io.*;

def getLookupTableValue(value, tableId, serviceProvider) {
	if (value == null || !tableId || serviceProvider == null) {
		return null
	}
	def lookupService = serviceProvider.getService(LookupService.class)
	def tableInfo = lookupService?.getTable(tableId)
	def result = tableInfo?.getTable()?.lookup(value.toString() as Value) as String
	if (result == null || result.equalsIgnoreCase('null')) {
		return null
	}
	return result
}

def collectAttributeZweckbestimmung(featureTypeName) {
	if (_source.p.zweckbestimmung.value()) {
		collectAttributeAndFeatureTypeNameById('zweckbestimmung', _source.p.id.value(), featureTypeName)
	}
}

def collectAttributeDetailZweckbestimmung(featureTypeName) {
	if (_source.p.detaillierteZweckbestimmung.value()) {
		collectAttributeAndFeatureTypeNameById('detaillierteZweckbestimmung', _source.p.id.value(), featureTypeName)
	}
}

def collectAttributeSondernutzung(featureTypeName) {
	if (_source.p.sonderNutzung.value() || _source.p.sondernutzung.value()) {
		collectAttributeAndFeatureTypeNameById('sondernutzung', _source.p.id.value(), featureTypeName)
	}
}

def collectAttributeDetailSondernutzung(featureTypeName) {
	if (_source.p.detaillierteSondernutzung.value()) {
		collectAttributeAndFeatureTypeNameById('detaillierteSondernutzung', _source.p.id.value(), featureTypeName)
	}
}

def collectAttributeAndFeatureTypeNameById(attributeName, id, featureTypeName) {
	if (!id || !featureTypeName) {
		return
	}
	withTransformationContext{
		def c = _.context.collector(it)
		c.complexeAttribute.sourceAttributeName[id] << attributeName
		c.complexeAttribute.sourceType[id] << featureTypeName
	}
}
