package github.com.foodactioncommitteecookbook.map;

import github.com.foodactioncommitteecookbook.R;

/**
 * An enumeration of regions in Nova Scotia.
 */
public enum Region {
  Amherst(R.string.amherst),
  Antigonish(R.string.antigonish),
  Berwick(R.string.berwick),
  Bridgetown(R.string.bridgetown),
  Bridgewater(R.string.bridgewater),
  Chester(R.string.chester),
  Digby(R.string.digby),
  Enfield(R.string.enfield),
  GlaceBay(R.string.glace_bay),
  Greenwood(R.string.greenwood),
  Halifax(R.string.halifax),
  Hantsport(R.string.hantsport),
  Inverness(R.string.inverness),
  Kentville(R.string.kentville),
  LakeEcho(R.string.lake_echo),
  Liverpool(R.string.liverpool),
  Lunenburg(R.string.lunenburg),
  Middleton(R.string.middleton),
  NewGlasgow(R.string.new_gasgow),
  NewWaterford(R.string.new_waterford),
  Oxford(R.string.oxford),
  Parrsboro(R.string.parrsboro),
  Pictou(R.string.pictou),
  PortHawkesbury(R.string.port_hawkesbury),
  Shelburne(R.string.shelburne),
  Springhill(R.string.springhill),
  Sydney(R.string.sydney),
  Truro(R.string.truro),
  Windsor(R.string.windsor),
  Wolfville(R.string.wolfville),
  Yarmouth(R.string.yarmouth);

  private int resId;

  Region(int resId) {
    this.resId = resId;
  }

  public int getResourceId() {
    return resId;
  }
}
